package com.sky.base.cache.redis.support;

import com.sky.base.cache.redis.handle.*;

/**
 * @author zzq
 * @version v1.0
 * @Title com.ursa.base.cache.redis.support.RedisSupport
 * @Description redis缓存支持
 * @date 2018/12/11
 */
public interface RedisSupport {

    /**
     * 获取字符串处理器
     *
     * @return
     */
    ValueOperator valueOperator();

    /**
     * 获取哈希处理器
     *
     * @return
     */
    HashOperator hashOperator();

    /**
     * 获取List处理器
     *
     * @return
     */
    ListOperator listOperator();

    /**
     * 获取Object处理器
     *
     * @return
     */
    ObjectOperator objectOperator();

    /**
     * 获取key处理器
     *
     * @return
     */
    Operator baseOperator();
}
