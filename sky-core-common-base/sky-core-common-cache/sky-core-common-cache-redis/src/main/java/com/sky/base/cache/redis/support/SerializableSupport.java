package com.sky.base.cache.redis.support;

/**
 * @author zzq
 * @version 1.0.0
 * @Title com.ursa.base.cache.redis.support.SerializableSupport
 * @Description 序列化支持接口
 * @date 2018/12/24
 */
public interface SerializableSupport {

    /**
     * 序列化
     *
     * @param obj 值
     * @return
     */
    String serializable(Object obj);

    /**
     * 反序列化
     *
     * @param value         值
     * @param specificClass 指定类型
     * @param <T>
     * @return
     */
    <T> T deserialize(String value, Class<T> specificClass);
}
