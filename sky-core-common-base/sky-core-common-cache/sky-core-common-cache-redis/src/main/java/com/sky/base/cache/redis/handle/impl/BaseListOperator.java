package com.sky.base.cache.redis.handle.impl;

import com.sky.base.cache.redis.config.RedisProperties;
import com.sky.base.cache.redis.handle.ListOperator;
import com.sky.base.lang.string.StringUtils;

import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.List;

/**
 * @author zzq
 * @version v1.0
 * @Title com.ursa.base.cache.redis.handle.impl.BaseListOperator
 * @Description 默认List操作者
 * @date 2018/12/11
 */
public class BaseListOperator extends AbstractOperator implements ListOperator {

    public BaseListOperator(JedisPool pool, RedisProperties properties) {
        super(pool, properties);
    }

    @Override
    public Long set(String key, List<String> value) {
        return execute(jedis -> jedis.lpush(key, value.toArray(new String[]{})));
    }

    @Override
    public List<String> get(String key) {
        return execute(jedis -> {
            List<String> lists = jedis.lrange(key, 0, -1);
            Collections.reverse(lists);
            return lists;
        });
    }

    @Override
    public <T> Long setT(String key, List<T> value) {
        return execute(jedis -> {
            List<String> list = newInstance(value);
            if (null == value) {
                return null;
            }
            for (T va : value) {
                list.add(serializable(va));
            }
            return jedis.lpush(key, list.toArray(new String[]{}));
        });
    }

    @Override
    public <T> List<T> get(String key, Class<T> specificClass) {
        return execute(jedis -> {
            List<String> list = jedis.lrange(key, 0, -1);
            List<T> value = newInstance(list);
            if (null == value) {
                return null;
            }
            for (String str : list) {
                value.add(deserialize(str, specificClass));
            }
            return value;
        });
    }

    @Override
    public String get(String key, int index) {
        return execute(jedis -> jedis.lindex(key, index));
    }

    @Override
    public <T> T get(String key, Class<T> specificClass, int index) {
        return execute(jedis -> {
            String value = jedis.lindex(key, index);
            if (StringUtils.isBlank(value)) {
                return null;
            }
            return deserialize(value, specificClass);
        });
    }

    @Override
    public Long size(String key) {
        return execute(jedis -> jedis.llen(key));
    }

    @Override
    public Long set(String key, String value, Direction direction) {
        return execute(jedis -> {
            switch (direction) {
                case LEFT: {
                    return jedis.lpush(key, value);
                }
                case RIGHT: {
                    return jedis.rpush(key, value);
                }
                default: {
                    throw new RuntimeException("unknown direction type");
                }
            }
        });
    }

    @Override
    public <T> Long set(String key, T value, Direction direction) {
        return execute(jedis -> {
            switch (direction) {
                case LEFT: {
                    return jedis.lpush(key, serializable(value));
                }
                case RIGHT: {
                    return jedis.rpush(key, serializable(value));
                }
                default: {
                    throw new RuntimeException("unknown direction type");
                }
            }
        });
    }

    @Override
    public String del(String key, Direction direction) {
        return execute(jedis -> {
            switch (direction) {
                case LEFT: {
                    return jedis.lpop(key);
                }
                case RIGHT: {
                    return jedis.rpop(key);
                }
                default: {
                    throw new RuntimeException("unknown direction type");
                }
            }
        });
    }

    @Override
    public <T> T del(String key, Class<T> specificClass, Direction direction) {
        return execute(jedis -> {
            switch (direction) {
                case LEFT: {
                    return deserialize(jedis.lpop(key), specificClass);
                }
                case RIGHT: {
                    return deserialize(jedis.rpop(key), specificClass);
                }
                default: {
                    throw new RuntimeException("unknown direction type");
                }
            }
        });
    }

    public enum Direction {
        /**
         * 左
         */
        LEFT,
        /**
         * 右
         */
        RIGHT
    }
}
