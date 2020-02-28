package com.sky.base.cache.redis;

import java.util.Map;

import com.sky.base.cache.CacheManager;
import com.sky.base.cache.redis.client.RedisClient;
import com.sky.base.cache.redis.constants.RedisConstants;

/**
 * @author lizp
 * @version 1.0.0
 * @Title
 * @Description
 * @date 2019/4/25
 */
public class RedisCacheManager implements CacheManager {

    private RedisClient redisClient;

    @Override
    public <T> T get(String cacheName, String key, Class<T> type) {
        return redisClient.hget(cacheName, key, type);
    }

    @Override
    public void put(String cacheName, String key, Object value) {
        if (!redisClient.hset(cacheName, key, value).equals(RedisConstants.L_OK)) {
            throw new RuntimeException("Cache failed");
        }
    }

    @Override
    public void putAll(String cacheName, Map<String, Object> map) {
        if (!redisClient.hmsetByObj(cacheName, map)) {
            throw new RuntimeException("Cache failed");
        }
    }

    @Override
    public void evict(String cacheName, String key) {
        redisClient.hdel(cacheName, key);
    }

    @Override
    public void clear(String cacheName) {
        redisClient.expire(cacheName, -1);
    }

    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }
}
