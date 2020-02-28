package com.sky.base.cache.redis.support;

import com.sky.base.cache.redis.enumeration.RedisSerializableEnum;

/**
 * @author zzq
 * @version 1.0.0
 * @Title com.ursa.base.cache.redis.support.SerializableSupportFactory
 * @Description 序列化支持工厂
 * @date 2018/12/24
 */
public interface SerializableSupportFactory {

    /**
     * 获取序列化支持
     *
     * @param type
     * @return
     */
    SerializableSupport newInstance(RedisSerializableEnum type);
}
