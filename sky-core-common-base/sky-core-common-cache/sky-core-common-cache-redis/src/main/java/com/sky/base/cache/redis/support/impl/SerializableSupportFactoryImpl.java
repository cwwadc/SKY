package com.sky.base.cache.redis.support.impl;

import com.sky.base.cache.redis.enumeration.RedisSerializableEnum;
import com.sky.base.cache.redis.support.SerializableSupport;
import com.sky.base.cache.redis.support.SerializableSupportFactory;
import com.sky.base.serialize.json.JsonUtils;

/**
 * @author zzq
 * @version 1.0.0
 * @Title com.ursa.base.cache.redis.support.impl.SerializableSupportFactoryImpl
 * @Description 序列化支持工厂实现
 * @date 2018/12/24
 */
public class SerializableSupportFactoryImpl implements SerializableSupportFactory {

    private SerializableSupportFactoryImpl() {
    }

    private final static SerializableSupportFactory FACTORY = new SerializableSupportFactoryImpl();

    public static SerializableSupportFactory getSingleInstance() {
        return FACTORY;
    }

    @Override
    public SerializableSupport newInstance(RedisSerializableEnum type) {
        switch (type) {
            case JSON: {
                return new SerializableSupport() {
                    @Override
                    public String serializable(Object obj) {
                        return JsonUtils.toJsonString(obj);
                    }

                    @Override
                    public <T> T deserialize(String value, Class<T> specificClass) {
                        return JsonUtils.parse(value, specificClass);
                    }
                };
            }
            default: {
                throw new RuntimeException("unknown redis serializable type");
            }
        }
    }
}
