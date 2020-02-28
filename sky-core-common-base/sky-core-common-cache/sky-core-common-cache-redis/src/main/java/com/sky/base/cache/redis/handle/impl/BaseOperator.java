package com.sky.base.cache.redis.handle.impl;

import com.sky.base.cache.redis.config.RedisProperties;
import com.sky.base.cache.redis.constants.RedisConstants;
import com.sky.base.cache.redis.handle.Operator;

import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

/**
 * @author zzq
 * @version v1.0
 * @Title com.ursa.base.cache.redis.handle.impl.BaseOperator
 * @Description 通用操作者
 * @date 2018/12/11
 */
public class BaseOperator extends AbstractOperator implements Operator {

    public BaseOperator(JedisPool pool, RedisProperties properties) {
        super(pool, properties);
    }

    @Override
    public String incr(String key) {
        return execute(jedis -> String.valueOf(jedis.incr(key)));
    }

    @Override
    public String incr(String key, int n) {
        return execute(jedis -> String.valueOf(jedis.incrBy(key, n)));
    }

    @Override
    public String decr(String key) {
        return execute(jedis -> String.valueOf(jedis.decr(key)));
    }

    @Override
    public String decr(String key, int n) {
        return execute(jedis -> String.valueOf(jedis.decrBy(key, n)));
    }

    @Override
    public Boolean expire(String key, int seconds) {
        return execute(jedis -> RedisConstants.L_OK.equals(jedis.expire(key, seconds)));
    }

    @Override
    public Boolean persist(String key) {
        return execute(jedis -> RedisConstants.L_OK.equals(jedis.persist(key)));
    }

    @Override
    public Boolean exists(String key) {
        return execute(jedis -> jedis.exists(key));
    }

    @Override
    public Long del(String... key) {
        return execute(jedis -> jedis.del(key));
    }

    @Override
    public String type(String key) {
        return execute(jedis -> jedis.type(key));
    }

    @Override
    public Long lifeTime(String key) {
        return execute(jedis -> jedis.ttl(key));
    }

    @Override
    public Set<String> keys(String patten) {
        return execute(jedis -> jedis.keys(patten));
    }

    @Override
    public Object eval(String script) {
        return execute(jedis -> jedis.eval(script));
    }

    @Override
    public Object eval(String script, List<String> keys, List<String> args) {
        return execute(jedis -> jedis.eval(script, keys, args));
    }
}
