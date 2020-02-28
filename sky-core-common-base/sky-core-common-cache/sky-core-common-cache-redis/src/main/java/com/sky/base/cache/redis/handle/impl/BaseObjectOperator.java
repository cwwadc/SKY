package com.sky.base.cache.redis.handle.impl;

import com.sky.base.cache.redis.config.RedisProperties;
import com.sky.base.cache.redis.constants.RedisConstants;
import com.sky.base.cache.redis.handle.ObjectOperator;

import redis.clients.jedis.JedisPool;

/**
 * @author zzq
 * @version v1.0
 * @Title com.ursa.base.cache.redis.handle.impl.BaseObjectOperator
 * @Description 默认object操作者
 * @date 2018/12/11
 */
public class BaseObjectOperator extends AbstractOperator implements ObjectOperator {

    public BaseObjectOperator(JedisPool pool, RedisProperties properties) {
        super(pool, properties);
    }

    @Override
    public Boolean set(String key, Object value) {
        return execute(jedis -> {
            String res = jedis.set(key, serializable(value));
            return RedisConstants.S_OK.equals(res);
        });
    }

    @Override
    public Boolean set(String key, Object value, boolean isDuplicateJudge) {
        return execute(jedis -> {
            if (isDuplicateJudge) {
                Long res = jedis.setnx(key, serializable(value));
                return RedisConstants.L_OK.equals(res);
            } else {
                String res = jedis.set(key, serializable(value));
                return RedisConstants.S_OK.equals(res);
            }
        });
    }

    @Override
    public Boolean set(String key, Object value, int seconds) {
        return execute(jedis -> {
            String res = jedis.setex(key, seconds, serializable(value));
            return RedisConstants.S_OK.equals(res);
        });
    }

    @Override
    public <T> T get(String key, Class<T> specificClass) {
        return execute(jedis -> deserialize(jedis.get(key), specificClass));
    }

    @Override
    public <T> T getSet(String key, Object value, Class<T> specificClass) {
        return execute(jedis -> {
            String res = jedis.getSet(key, serializable(value));
            return deserialize(res, specificClass);
        });
    }

}
