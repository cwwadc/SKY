package com.sky.base.cache.redis.client.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sky.base.cache.redis.client.RedisClient;
import com.sky.base.cache.redis.config.RedisProperties;
import com.sky.base.cache.redis.constants.RedisConstants;
import com.sky.base.cache.redis.handle.HashOperator;
import com.sky.base.cache.redis.handle.ListOperator;
import com.sky.base.cache.redis.handle.ObjectOperator;
import com.sky.base.cache.redis.handle.Operator;
import com.sky.base.cache.redis.handle.ValueOperator;
import com.sky.base.cache.redis.handle.impl.BaseListOperator;
import com.sky.base.cache.redis.support.RedisSupport;
import com.sky.base.cache.redis.support.impl.RedisSupportImpl;
import com.sky.base.lang.string.StringUtils;

/**
 * @author zzq
 * @version v1.0
 * @Title com.ursa.base.cache.redis.client.impl.RedisClientImpl
 * @Description redis操作客户端实现
 * @date 2018/12/17
 */
public class RedisClientImpl implements RedisClient {

	private Operator baseOperator;
	private ValueOperator valueOperator;
	private ObjectOperator objectOperator;
	private ListOperator listOperator;
	private HashOperator hashOperator;

	public RedisClientImpl(RedisProperties properties) {
		if (null == properties) {
			throw new IllegalArgumentException("RedisProperties == null");
		}
		RedisSupport redisSupport = new RedisSupportImpl(properties);
		baseOperator = redisSupport.baseOperator();
		valueOperator = redisSupport.valueOperator();
		objectOperator = redisSupport.objectOperator();
		listOperator = redisSupport.listOperator();
		hashOperator = redisSupport.hashOperator();
	}

	@Override
	public String incr(String key) {
		return baseOperator.incr(key);
	}

	@Override
	public String incrBy(String key, int n) {
		return baseOperator.incr(key, n);
	}

	@Override
	public String decr(String key) {
		return baseOperator.decr(key);
	}

	@Override
	public String decrBy(String key, int n) {
		return baseOperator.decr(key, n);
	}

	@Override
	public Boolean expire(String key, int seconds) {
		return baseOperator.expire(key, seconds);
	}

	@Override
	public Boolean persist(String key) {
		return baseOperator.persist(key);
	}

	@Override
	public Boolean exists(String key) {
		return baseOperator.exists(key);
	}

	@Override
	public Long del(String... key) {
		return baseOperator.del(key);
	}

	@Override
	public String type(String key) {
		return baseOperator.type(key);
	}

	@Override
	public Long ttl(String key) {
		return baseOperator.lifeTime(key);
	}

	@Override
	public Set<String> keys(String patten) {
		return baseOperator.keys(patten);
	}

	@Override
	public Object eval(String script) {
		return baseOperator.eval(script);
	}

	@Override
	public Object eval(String script, List<String> keys, List<String> args) {
		return baseOperator.eval(script, keys, args);
	}

	@Override
	public Boolean set(String key, Object value) {
		if (value instanceof String) {
			return valueOperator.set(key, (String) value);
		}
		return objectOperator.set(key, value);
	}

	@Override
	public Boolean setnx(String key, Object value) {
		if (value instanceof String) {
			return valueOperator.set(key, (String) value, true);
		}
		return objectOperator.set(key, value, true);
	}

	@Override
	public Boolean setex(String key, Object value, int seconds) {
		if (value instanceof String) {
			return valueOperator.set(key, (String) value, seconds);
		}
		return objectOperator.set(key, value, seconds);
	}

	@Override
	public String getSet(String key, String value) {
		return valueOperator.getSet(key, value);
	}

	@Override
	public <T> T getSetByObj(String key, Object value, Class<T> specificClass) {
		return objectOperator.getSet(key, value, specificClass);
	}

	@Override
	public <T> T get(String key, Class<T> specificClass) {
		if (String.class.equals(specificClass)) {
			return (T) valueOperator.get(key);
		}
		return objectOperator.get(key, specificClass);
	}

	@Override
	public Long append(String key, String str) {
		return valueOperator.append(key, str);
	}

	@Override
	public Long lpush(String key, List<String> value) {
		return listOperator.set(key, value);
	}

	@Override
	public <T> Long lpushByObj(String key, List<T> value) {
		return listOperator.setT(key, value);
	}

	@Override
	public <T> List<T> lrangeAll(String key, Class<T> specificClass) {
		if (String.class.equals(specificClass)) {
			return (List<T>) listOperator.get(key);
		}
		return listOperator.get(key, specificClass);
	}

	@Override
	public <T> T lpop(String key, Class<T> specificClass) {
		if (String.class.equals(specificClass)) {
			return (T) listOperator.del(key, BaseListOperator.Direction.LEFT);
		}
		return listOperator.del(key, specificClass, BaseListOperator.Direction.LEFT);
	}

	@Override
	public <T> T rpop(String key, Class<T> specificClass) {
		if (String.class.equals(specificClass)) {
			return (T) listOperator.del(key, BaseListOperator.Direction.RIGHT);
		}
		return listOperator.del(key, specificClass, BaseListOperator.Direction.RIGHT);

	}

	@Override
	public Long lpush(String key, Object value) {
		if (value instanceof String) {
			return listOperator.set(key, (String) value, BaseListOperator.Direction.LEFT);
		}
		return listOperator.set(key, value, BaseListOperator.Direction.LEFT);
	}

	@Override
	public Long rpush(String key, Object value) {
		if (value instanceof String) {
			return listOperator.set(key, (String) value, BaseListOperator.Direction.RIGHT);
		}
		return listOperator.set(key, value, BaseListOperator.Direction.RIGHT);
	}

	@Override
	public Long llen(String key) {
		return listOperator.size(key);
	}

	@Override
	public Boolean hmset(String key, Map<String, String> map) {
		return hashOperator.set(key, map);
	}

	@Override
	public <T> Boolean hmsetByObj(String key, Map<String, T> map) {
		return hashOperator.setT(key, map);
	}

	@Override
	public <T> Long hset(String key, String mapKey, T mapValue) {
		if (mapValue instanceof String) {
			return hashOperator.set(key, mapKey, (String) mapValue);
		}
		return hashOperator.set(key, mapKey, mapValue);
	}

	@Override
	public <T> Map<String, T> hgetAll(String key, Class<T> specificClass) {
		if (String.class.equals(specificClass)) {
			return (Map<String, T>) hashOperator.get(key);
		}
		return hashOperator.get(key, specificClass);
	}

	@Override
	public <T> T hget(String key, String mapKey, Class<T> specificClass) {
		if (String.class.equals(specificClass)) {
			return (T) hashOperator.get(key, mapKey);
		}
		return hashOperator.get(key, mapKey, specificClass);
	}

	@Override
	public Boolean hdel(String key, String... mapKey) {
		return hashOperator.del(key, mapKey);
	}

	@Override
	public Boolean hexists(String key, String mapKey) {
		return hashOperator.exists(key, mapKey);
	}

	@Override
	public Set<String> hkeys(String key) {
		return hashOperator.getMapKeys(key);
	}

	@Override
	public <T> List<T> hvals(String key, Class<T> specificClass) {
		if (String.class.equals(specificClass)) {
			return (List<T>) hashOperator.getMapValues(key);
		}
		return hashOperator.getMapValues(key, specificClass);
	}

	@Override
	public <T> List<T> hmget(String key, Class<T> specificClass, String... mapKeys) {
		return hashOperator.mget(key, specificClass, mapKeys);
	}

	@Override
	public Boolean set(String key, String value, String nxxx, String expx, long expireTime) {
		return StringUtils.equals(valueOperator.set(key, value, nxxx, expx, expireTime), RedisConstants.S_OK);
	}
}
