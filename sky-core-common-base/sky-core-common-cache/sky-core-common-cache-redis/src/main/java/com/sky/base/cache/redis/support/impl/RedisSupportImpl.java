package com.sky.base.cache.redis.support.impl;

import com.sky.base.cache.redis.config.RedisProperties;
import com.sky.base.cache.redis.handle.*;
import com.sky.base.cache.redis.handle.impl.*;
import com.sky.base.cache.redis.support.RedisSupport;
import com.sky.base.lang.string.StringUtils;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * @author zzq
 * @version v1.0
 * @Title com.ursa.base.cache.redis.support.impl.RedisSupportImpl
 * @Description redis缓存支持实现
 * @date 2018/12/11
 */
public class RedisSupportImpl implements RedisSupport {

    private JedisPool pool;
    private RedisProperties properties;

    public RedisSupportImpl(RedisProperties properties) {
        pool = doCreateJedisPool(properties);
        this.properties = properties;
    }

    private JedisPool doCreateJedisPool(RedisProperties properties) {
        if (StringUtils.isBlank(properties.getHost())) {
            throw new IllegalArgumentException("redis properties host is null");
        }
        properties.init();
        JedisPoolConfig config = new JedisPoolConfig();
        // 最大连接数，连接全部用完，进行等待
        config.setMaxTotal(properties.getMaxTotal());
        // 最小空余数
        config.setMinIdle(properties.getMinIdle());
        // 最大空余数
        config.setMaxIdle(properties.getMaxIdle());
        //获取jedis客户端时是否验证客户端可用
        config.setTestOnBorrow(properties.isTestOnBorrow());
        //等待可用连接的最大时间
        config.setMaxWaitMillis(properties.getMaxWait());
        if (properties.isAuth()) {
            if (StringUtils.isBlank(properties.getPassword())) {
                throw new IllegalArgumentException("redis properties password is null");
            }
            //需要授权 设置密码
            return new JedisPool(config, properties.getHost(), properties.getPort(), properties.getTimeout(),
                    properties.getPassword());
        } else {
            //不需要授权
            return new JedisPool(config, properties.getHost(), properties.getPort(), properties.getTimeout());
        }
    }

    @Override
    public ValueOperator valueOperator() {
        return new BaseValueOperator(pool, properties);
    }

    @Override
    public HashOperator hashOperator() {
        return new BaseHashOperator(pool, properties);
    }

    @Override
    public ListOperator listOperator() {
        return new BaseListOperator(pool, properties);
    }

    @Override
    public ObjectOperator objectOperator() {
        return new BaseObjectOperator(pool, properties);
    }

    @Override
    public Operator baseOperator() {
        return new BaseOperator(pool, properties);
    }

}
