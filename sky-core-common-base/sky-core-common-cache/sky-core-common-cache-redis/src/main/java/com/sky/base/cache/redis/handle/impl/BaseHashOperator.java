package com.sky.base.cache.redis.handle.impl;

import com.sky.base.cache.redis.config.RedisProperties;
import com.sky.base.cache.redis.constants.RedisConstants;
import com.sky.base.cache.redis.handle.HashOperator;

import redis.clients.jedis.JedisPool;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zzq
 * @version v1.0
 * @Title com.ursa.base.cache.redis.handle.impl.BaseHashOperator
 * @Description 默认哈希操作者
 * @date 2018/12/11
 */
public class BaseHashOperator extends AbstractOperator implements HashOperator {

	public BaseHashOperator(JedisPool pool, RedisProperties properties) {
		super(pool, properties);
	}

	@Override
	public Boolean set(String key, Map<String, String> map) {
		return execute(jedis -> RedisConstants.S_OK.equals(jedis.hmset(key, map)));
	}

	@Override
	public <T> Boolean setT(String key, Map<String, T> map) {
		return execute(jedis -> {
			Map<String, String> serializableMap = newInstance(map);
			if (serializableMap == null) {
				return null;
			}
			for (Map.Entry<String, T> m : map.entrySet()) {
				serializableMap.put(m.getKey(), serializable(m.getValue()));
			}
			return RedisConstants.S_OK.equals(jedis.hmset(key, serializableMap));
		});
	}

	@Override
	public Long set(String key, String mapKey, String mapValue) {
		return execute(jedis -> jedis.hset(key, mapKey, mapValue));
	}

	@Override
	public <T> Long set(String key, String mapKey, T mapValue) {
		return execute(jedis -> jedis.hset(key, mapKey, serializable(mapValue)));
	}

	@Override
	public Map<String, String> get(String key) {
		return execute(jedis -> jedis.hgetAll(key));
	}

	@Override
	public <T> Map<String, T> get(String key, Class<T> specificClass) {
		return execute(jedis -> {
			Map<String, String> map = jedis.hgetAll(key);
			Map<String, T> deserializeMap = newInstance(map);
			if (deserializeMap == null) {
				return null;
			}
			for (Map.Entry<String, String> m : map.entrySet()) {
				deserializeMap.put(m.getKey(), deserialize(m.getValue(), specificClass));
			}
			return deserializeMap;
		});
	}

	@Override
	public String get(String key, String mapKey) {
		return execute(jedis -> jedis.hget(key, mapKey));
	}

	@Override
	public <T> T get(String key, String mapKey, Class<T> specificClass) {
		return execute(jedis -> deserialize(jedis.hget(key, mapKey), specificClass));
	}

	@Override
	public Set<String> getMapKeys(String key) {
		return execute(jedis -> jedis.hkeys(key));
	}

	@Override
	public List<String> getMapValues(String key) {
		return execute(jedis -> jedis.hvals(key));
	}

	@Override
	public <T> List<T> getMapValues(String key, Class<T> specificClass) {
		return execute(jedis -> {
			List<String> lists = jedis.hvals(key);
			List<T> values = newInstance(lists);
			if (values == null) {
				return null;
			}
			for (String str : lists) {
				values.add(deserialize(str, specificClass));
			}
			return values;
		});
	}

	@Override
	public Boolean del(String key, String... mapKey) {
		return execute(jedis -> RedisConstants.L_OK.equals(jedis.hdel(key, mapKey)));
	}

	@Override
	public Boolean exists(String key, String mapKey) {
		return execute(jedis -> jedis.hexists(key, mapKey));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> mget(String key, Class<T> specificClass, String... mapKeys) {
		return execute(jedis -> {
			List<String> values = jedis.hmget(key, mapKeys);
			if (values == null) {
				return Collections.EMPTY_LIST;
			}
			if (specificClass == null || String.class.isAssignableFrom(specificClass)) {
				return (List<T>) values;
			}
			List<T> objects = new ArrayList<>(values.size());
			for (String each : values) {
				if (StringUtils.equalsIgnoreCase(each, "nil")) {
					continue;
				}
				objects.add(deserialize(each, specificClass));
			}
			return objects;
		});
	}

}
