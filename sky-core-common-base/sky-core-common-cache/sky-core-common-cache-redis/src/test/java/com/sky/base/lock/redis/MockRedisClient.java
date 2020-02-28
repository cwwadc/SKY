package com.sky.base.lock.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sky.base.cache.redis.client.RedisClient;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-01-16
 */
public class MockRedisClient implements RedisClient {

    @Override
    public String incr(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String incrBy(String key, int n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String decr(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String decrBy(String key, int n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean expire(String key, int seconds) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean persist(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean exists(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long del(String... key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String type(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long ttl(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> keys(String patten) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object eval(String script) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object eval(String script, List<String> keys, List<String> args) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean set(String key, Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean setnx(String key, Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean setex(String key, Object value, int seconds) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getSet(String key, String value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T getSetByObj(String key, Object value, Class<T> specificClass) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T get(String key, Class<T> specificClass) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long append(String key, String str) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long lpush(String key, List<String> value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> Long lpushByObj(String key, List<T> value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> List<T> lrangeAll(String key, Class<T> specificClass) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T lpop(String key, Class<T> specificClass) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T rpop(String key, Class<T> specificClass) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long lpush(String key, Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long rpush(String key, Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long llen(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean hmset(String key, Map<String, String> map) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> Boolean hmsetByObj(String key, Map<String, T> map) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> Long hset(String key, String mapKey, T mapValue) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> Map<String, T> hgetAll(String key, Class<T> specificClass) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T hget(String key, String mapKey, Class<T> specificClass) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean hdel(String key, String... mapKey) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean hexists(String key, String mapKey) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> hkeys(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> List<T> hvals(String key, Class<T> specificClass) {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public <T> List<T> hmget(String key, Class<T> specificClass, String... mapKeys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean set(String key, String value, String nxxx, String expx, long expireTime) {
		// TODO Auto-generated method stub
		return null;
	}

    
}
