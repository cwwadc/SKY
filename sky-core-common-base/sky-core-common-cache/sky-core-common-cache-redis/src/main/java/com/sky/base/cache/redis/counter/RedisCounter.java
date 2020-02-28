package com.sky.base.cache.redis.counter;

import com.sky.base.cache.redis.client.RedisClient;
import com.sky.base.context.counter.Counter;
import com.sky.base.lang.time.DateTimeUtils;
import com.sky.base.lock.api.DistributedLock;
import com.sky.base.lock.exception.TryLockException;
import com.sky.base.lock.support.DefaultLockOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author zzq
 * @version 1.0.0
 * @Title {@link}
 * @Description
 * @date 2019/5/13
 */
public class RedisCounter implements Counter {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisCounter.class);

	private static final String DEFAULT_COUNTER_PREFIX = "counter:";

	/**
	 * 记数间隔时间
	 */
	private Integer intervalTime;
	/**
	 * 周期内频率
	 */
	private Integer cycleFrequency;
	/**
	 * 步长
	 */
	private Integer stepSize;
	/**
	 * 周期类型
	 */
	private Cycle cycle;

	private RedisClient redisClient;

	private DistributedLock distributedLock;

	private String counterPrefix = DEFAULT_COUNTER_PREFIX;

	public RedisCounter(RedisClient redisClient, DistributedLock distributedLock) {
		this(60, 6, 1, Cycle.DAY, redisClient, distributedLock);
	}

	public RedisCounter(Integer intervalTime, Integer cycleFrequency, Integer stepSize, Cycle cycle,
	        RedisClient redisClient, DistributedLock distributedLock) {
		this.intervalTime = intervalTime;
		this.cycleFrequency = cycleFrequency;
		this.stepSize = stepSize;
		this.cycle = cycle;
		this.redisClient = redisClient;
		this.distributedLock = distributedLock;
	}

	@Override
	public Boolean isAllowInterval(String key) {
		return sync(key, () -> checkAllowInterval(key));
	}

	private Boolean checkAllowInterval(String key) {
		CountUnit unit = getCountUnit(key);
		long l = System.currentTimeMillis();
		if (l > unit.getCycleFrequencyExpiresTime()) {
			return true;
		}
		return l > unit.getIntervalExpiresTime();
	}

	private CountUnit getCountUnit(String key) {
		CountUnit cacheCountUtil = redisClient.get(getCacheKey(key), CountUnit.class);
		return Optional.ofNullable(cacheCountUtil).orElseGet(CountUnit::new);
	}

	@Override
	public Boolean isAllowTimes(String key) {
		return sync(key, () -> checkAllowTimes(key));
	}

	private Boolean checkAllowTimes(String key) {
		CountUnit unit = getCountUnit(key);
		long l = System.currentTimeMillis();
		if (l > unit.getCycleFrequencyExpiresTime()) {
			return true;
		}
		return cycleFrequency > unit.getNum();
	}

	@Override
	public Boolean isAvailable(String key) {
		return sync(key, () -> checkAllowInterval(key) && checkAllowInterval(key));
	}

	private Boolean sync(String key, Supplier<Boolean> supplier) {
		try {
			DefaultLockOperator<Boolean> lockOperator = new DefaultLockOperator<Boolean>(distributedLock,
			        getLockKey(key), supplier);
			return lockOperator.tryLockWithTimeoutExecute();
		} catch (TryLockException e) {
			throw new IllegalStateException("系统繁忙");
		}
	}

	@Override
	public Boolean incr(String key) {
		return sync(key, () -> doIncr(key));
	}

	protected Boolean doIncr(String key) {
		CountUnit unit = getCountUnit(key);
		LOGGER.info("获取计数器单元数据 : {}", unit);
		long l = System.currentTimeMillis();
		unit.addNum(stepSize);
		refreshUtilTime(unit, l);
		LOGGER.info("累加计数器单元数据 : {}", unit);
		return redisClient.setex(getCacheKey(key), unit, getExpiresSeconds(unit, l));
	}

	private void refreshUtilTime(CountUnit unit, long l) {
		if (cycle == Cycle.DAY) {
			long dayEnd = DateTimeUtils.getDayEnd(new Date()).getTime();
			unit.setCycleFrequencyExpiresTime(dayEnd);
			unit.setIntervalExpiresTime(l + intervalTime * 1000);
		} else {
			throw new IllegalArgumentException("未知参数类型");
		}
	}

	private int getExpiresSeconds(CountUnit unit, long l) {
		return (int) ((unit.getCycleFrequencyExpiresTime() - l) / 1000);
	}

	private String getLockKey(String key) {
		return counterPrefix + "lock:" + key;
	}

	private String getCacheKey(String key) {
		return counterPrefix + "cache:" + key;
	}

	public void setCounterPrefix(String counterPrefix) {
		this.counterPrefix = counterPrefix;
	}

}
