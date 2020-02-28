package com.sky.base.cache;

import java.util.Map;

/**
 * @author lizp
 * @version 1.0.0
 * @Title
 * @Description
 * @date 2019/4/25
 */
public interface CacheManager {

    /**
     * 获取指定缓存对象
     * @param cacheName
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    <T> T get(String cacheName, String key, Class<T> type);

    /**
     * 保存对象至缓存
     * @param cacheName
     * @param key
     * @param value
     */
    void put(String cacheName, String key, Object value);

    /**
     * 保存对象集合到缓存
     * @param cacheName
     * @param map
     */
    void putAll(String cacheName, Map<String,Object> map);

    /**
     * 清除指定缓存对象
     * @param cacheName
     * @param key
     */
    void evict(String cacheName, String key);

    /**
     * 清除指定缓存
     * @param cacheName
     */
    void clear(String cacheName);
}
