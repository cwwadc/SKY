package com.sky.base.lock.redis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Title redis分布式锁对象集合
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-01-16
 */
final class RedisLockObjectMap {

    private final static Map<String, String> THREAD_LOCK_OBJECT_MAP = new ConcurrentHashMap<String, String>(32);

    static String put(final String key, final String value) {
        return THREAD_LOCK_OBJECT_MAP.put(getCurrentThreadId() + key, value);
    }

    static String get(final String key) {
        return THREAD_LOCK_OBJECT_MAP.get(getCurrentThreadId() + key);
    }

    static String remove(final String key) {
        return THREAD_LOCK_OBJECT_MAP.remove(getCurrentThreadId() + key);
    }

    private static Long getCurrentThreadId() {
        return Thread.currentThread().getId();
    }

    private RedisLockObjectMap() {
        super();
    }

}