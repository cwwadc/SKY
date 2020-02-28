package com.sky.base.cache.redis;

import com.sky.base.cache.redis.config.RedisProperties;
import com.sky.base.cache.redis.enumeration.RedisSerializableEnum;
import com.sky.base.cache.redis.support.RedisSupport;
import com.sky.base.cache.redis.support.impl.RedisSupportImpl;

import org.junit.Before;

/**
 * @author zzq
 * @version v1.0
 * @Title
 * @Description
 * @date 2018/12/11
 */
public class OperatorTest {
    private RedisSupport redisSupport;

    @Before
    public void before(){
        RedisProperties properties = new RedisProperties();
        properties.setAuth(false);
        properties.setHost("10.47.0.167");
        properties.setSerializableType(RedisSerializableEnum.JSON);
        redisSupport = new RedisSupportImpl(properties);
    }

    public RedisSupport getRedisSupport() {
        return redisSupport;
    }
}
