package com.sky.base.lock.redis;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.base.cache.redis.client.RedisClient;
import com.sky.base.lang.string.StringUtils;
import com.sky.base.cache.redis.constants.RedisConstants;
import com.sky.base.lock.api.DistributedLock;

/**
 * @Title 分布式锁，redis实现
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-01-16
 */
public class RedisDistributedLock implements DistributedLock {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisDistributedLock.class);

	/** 默认重试等待时间 */
	static final long DEFAULT_RETRY_AWAIT_TIME = 500;
	/** 默认锁过期时间 */
	static final long DEFAULT_EXPIRE_TIME = 15 * 60 * 1000L;
	/** 默认时间单位：毫秒 */
	static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;

	private final RedisClient redisClient;

	/**
	 * 锁重试等待时间
	 */
	private long retryAwaitTime = DEFAULT_RETRY_AWAIT_TIME;
	/**
	 * 锁过期时间
	 */
	private long expireTime = DEFAULT_EXPIRE_TIME;
	/**
	 * 时间单位
	 */
	private TimeUnit timeUnit = DEFAULT_TIME_UNIT;

	public RedisDistributedLock(RedisClient redisClient) {
		super();
		this.redisClient = redisClient;
	}

	public RedisDistributedLock(RedisClient redisClient, long retryAwaitTime, long expireTime) {
		this(redisClient);
		this.retryAwaitTime = retryAwaitTime;
		this.expireTime = expireTime;
	}

	public RedisDistributedLock(RedisClient redisClient, long retryAwaitTime, long expireTime, TimeUnit timeUnit) {
		this(redisClient, retryAwaitTime, expireTime);
		this.timeUnit = timeUnit;
	}

	/**
	 * 尝试获取资源锁
	 * <p/>
	 * 实现思路：<br/>
	 * set命令要用 set key value px milliseconds nx，替代setnx+expire需要分两次执行命令的方式，保证了原子性。
	 * <br/>
	 * value要具有唯一性，在解锁的时候就可以有依据，此处使用UUID.randomUUID().toString()方法生成。
	 * <p/>
	 * 可能存在的问题：<br/>
	 * 1、如线程的执行时间超过有效时间，则可能出现锁被其他线程抢占导致锁无效；
	 */
	@Override
	public boolean tryLock(String key) {
		checkArgument(key, "Key is blank");
		String value = getLockValue();
		return lock(key, value);
	}

	private void checkArgument(String arg, String msg) {
		if (StringUtils.isBlank(arg)) {
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * 执行lua脚本
	 * 
	 * @param luaScript
	 * @param key
	 * @param arg
	 * @return
	 */
	private Object redisEval(final String luaScript, final String key, final String arg) {
		return redisClient.eval(luaScript, Arrays.asList(key), Arrays.asList(arg));
	}

	private boolean lock(final String key, final String value) {
		if (redisClient.set(key, value, RedisConstants.SET_IF_NOT_EXIST,
		        RedisConstants.SET_WITH_EXPIRE_TIME_MILLISECONDS, timeUnit.toMillis(getExpireTime()))) {
			LOGGER.debug("成功获取锁, key -> {}", key);
			RedisLockObjectMap.put(key, value);
			return true;
		}
		LOGGER.debug("未获取到锁");
		return false;
	}

	private String getLockValue() {
		return UUID.randomUUID().toString();
	}

	@Override
	public boolean tryLock(String key, long timeout) {
		checkArgument(key, "Key is blank");
		long startTime = System.currentTimeMillis();
		for (;;) {
			if (tryLock(key)) {
				return true;
			}
			long now = System.currentTimeMillis();
			if ((now - startTime) >= timeout) {
				break;
			}
			doRetryAwait();
		}
		return false;
	}

	private void doRetryAwait() {
		try {
			LOGGER.debug("{}ms后重试", retryAwaitTime);
			timeUnit.sleep(retryAwaitTime);
		} catch (InterruptedException e) {
			LOGGER.warn("Happen Interrupt", e);
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public boolean lock(String key) {
		checkArgument(key, "Key is blank");
		for (;;) {
			if (tryLock(key)) {
				return true;
			}
			doRetryAwait();
		}
	}

	@Override
	public boolean unLock(String key) {
		checkArgument(key, "Key is blank");
		try {
			String expireTimeStr = RedisLockObjectMap.get(key);
			if (StringUtils.isEmpty(expireTimeStr)) {
				LOGGER.debug("未找到当前线程锁定的键值,锁释放失败, key -> {}", key);
				return false;
			}
			Optional<Object> object = Optional.ofNullable(redisEval(LuaScript.CHECK_EQUAL_DEL, key, expireTimeStr));
			if (object.isPresent()) {
				long affectNum = (Long) object.get();
				if (0L < affectNum) {
					LOGGER.debug("锁释放成功, key -> {}", key);
					return true;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Happen exception", e);
		} finally {
			RedisLockObjectMap.remove(key);
		}
		LOGGER.debug("锁释放失败, key -> {}", key);
		return false;
	}

	RedisClient getRedisClient() {
		return redisClient;
	}

	long getRetryAwaitTime() {
		return retryAwaitTime;
	}

	long getExpireTime() {
		return expireTime;
	}

	TimeUnit getTimeUnit() {
		return timeUnit;
	}

	private class LuaScript {

		/**
		 * 检查值是否相同，相同则删除
		 */
		private static final String CHECK_EQUAL_DEL = "local v = redis.call('GET', KEYS[1]); " //
		        + "local r = 0; " //
		        + "if v == ARGV[1] "//
		        + "then r = redis.call('DEL',KEYS[1]); " //
		        + "end " //
		        + "return r";
	}

}
