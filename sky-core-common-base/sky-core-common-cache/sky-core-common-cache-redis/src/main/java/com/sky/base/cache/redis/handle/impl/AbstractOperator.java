package com.sky.base.cache.redis.handle.impl;

import com.sky.base.cache.redis.config.RedisProperties;
import com.sky.base.cache.redis.enumeration.RedisSerializableEnum;
import com.sky.base.cache.redis.support.SerializableSupport;
import com.sky.base.cache.redis.support.impl.SerializableSupportFactoryImpl;
import com.sky.base.lang.string.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author zzq
 * @version v1.0
 * @Title com.ursa.base.cache.redis.handle.impl.AbstractOperator
 * @Description 抽象操作者
 * @date 2018/12/11
 */
abstract class AbstractOperator {
    static final Logger LOGGER = LoggerFactory.getLogger(AbstractOperator.class);

    private JedisPool pool;
    private SerializableSupport support;

    AbstractOperator(JedisPool pool, RedisProperties properties) {
        this.pool = pool;
        this.support = newSerializableSupport(properties);
    }

    private SerializableSupport newSerializableSupport(RedisProperties properties) {
        RedisSerializableEnum type = properties.getSerializableType();
        if (null == type) {
            type = RedisSerializableEnum.JSON;
            LOGGER.debug("{}:未设置序列化方式，设置默认序列化方式:{}", this.getClass().getName(), type);
        } else {
            LOGGER.debug("{}:设置序列化方式，序列化方式:{}", this.getClass().getName(), type);
        }
        return SerializableSupportFactoryImpl.getSingleInstance().newInstance(type);
    }

    /**
     * 打印异常日志
     *
     * @param e
     */
    private void loggerException(Exception e) {
        LOGGER.error("redis handle invoke error", e);
    }

    /**
     * 获取jedis客户端
     *
     * @return
     */
    private Jedis getJedis() {
        if (null != pool) {
            Jedis jedis = pool.getResource();
            if (null != jedis) {
                return jedis;
            }
            LOGGER.error("jedis pool get resource is null");
        } else {
            LOGGER.error("jedis pool is null");
        }
        return null;
    }

    <T> T newInstance(Object obj) {
        Class<?> clazz = obj.getClass();
        try {
            return (T) clazz.newInstance();
        } catch (Exception e) {
            LOGGER.error("init " + clazz + " error", e);
            return null;
        }
    }

    /**
     * 回收jedis客户端
     *
     * @param jedis
     */
    private void close(Jedis jedis) {
        if (null != jedis) {
            jedis.close();
        }
    }

    /**
     * 操作执行方法
     *
     * @param handler 具体处理器
     * @param <T>
     * @return
     */
    <T> T execute(Handler<T> handler) {
        Jedis jedis = getJedis();
        if (null == jedis) {
            return null;
        }
        long start = System.currentTimeMillis();
        try {
            LOGGER.debug("invoke redis handle start");
            return handler.doHandle(jedis);
        } catch (Exception e) {
            loggerException(e);
            return null;
        } finally {
            LOGGER.debug("invoke redis handle end times:{} ms", System.currentTimeMillis() - start);
            close(jedis);
        }
    }

    /**
     * 具体操作处理器
     *
     * @param <T>
     */
    interface Handler<T> {
        /**
         * 处理方法
         *
         * @param jedis jedis 客户端
         * @return
         */
        T doHandle(Jedis jedis);
    }

    String serializable(Object obj) {
        if (null == obj) {
            return null;
        }
        return support.serializable(obj);
    }

    <T> T deserialize(String value, Class<T> specificClass) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return support.deserialize(value, specificClass);
    }
}
