package com.sky.base.cache.redis.handle.impl;

import com.sky.base.cache.redis.config.RedisProperties;
import com.sky.base.cache.redis.constants.RedisConstants;
import com.sky.base.cache.redis.handle.ValueOperator;

import redis.clients.jedis.JedisPool;

/**
 * @author zzq
 * @version v1.0
 * @Title com.ursa.base.cache.redis.handle.impl.BaseValueOperator
 * @Description 默认字符串处理器
 * @date 2018/12/11
 */
public class BaseValueOperator extends AbstractOperator implements ValueOperator {

    public BaseValueOperator(JedisPool pool, RedisProperties properties) {
        super(pool, properties);
    }

    @Override
    public Boolean set(String key, String value) {
        return execute(jedis -> RedisConstants.S_OK.equals(jedis.set(key, value)));
    }

    @Override
    public Boolean set(String key, String value, boolean isDuplicateJudge) {
        return execute(jedis -> {
            if (isDuplicateJudge) {
                Long res = jedis.setnx(key, value);
                return RedisConstants.L_OK.equals(res);
            } else {
                String res = jedis.set(key, value);
                return RedisConstants.S_OK.equals(res);
            }
        });
    }

    @Override
    public Boolean set(String key, String value, int seconds) {
        return execute(jedis -> RedisConstants.S_OK.equals(jedis.setex(key, seconds, value)));
    }

    @Override
    public String getSet(String key, String value) {
        return execute(jedis -> jedis.getSet(key, value));
    }

    @Override
    public String get(String key) {
        return execute(jedis -> jedis.get(key));
    }

    @Override
    public Long append(String key, String str) {
        return execute(jedis -> jedis.append(key, str));
    }
    
    @Override
	public String set(String key, String value, String nxxx, String expx, long expireTime) {
		return execute(jedis -> jedis.set(key, value, nxxx, expx, expireTime));
	}
}
