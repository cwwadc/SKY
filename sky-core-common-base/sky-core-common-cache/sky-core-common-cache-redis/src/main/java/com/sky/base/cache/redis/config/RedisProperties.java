package com.sky.base.cache.redis.config;

import java.io.Serializable;

import com.sky.base.cache.redis.enumeration.RedisSerializableEnum;

/**
 * @author zzq
 * @version v1.0
 * @Title com.ursa.base.cache.redis.config.RedisProperties
 * @Description redis 配置bean
 * @date 2018/12/11
 */
public class RedisProperties implements Serializable {
    private static final long serialVersionUID = -1691014112733121693L;

    /**
     * 最大连接数
     */
    private Integer maxTotal;
    /**
     * 最小空余数
     */
    private Integer minIdle;
    /**
     * 最大空余数
     */
    private Integer maxIdle;
    /**
     * 地址
     */
    private String host;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 是否需要授权
     */
    private Boolean auth;
    /**
     * 授权密码
     */
    private String password;
    /**
     * 超时时间 单位毫秒
     */
    private Integer timeout;
    /**
     * 等待可用连接的最大时间，单位毫秒
     */
    private Integer maxWait;
    /**
     * 获取jedis客户端时是否验证客户端可用
     */
    private Boolean testOnBorrow;
    /**
     * 序列化类型
     */
    private RedisSerializableEnum serializableType;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RedisProperties{");
        sb.append("maxTotal=").append(maxTotal);
        sb.append(", minIdle=").append(minIdle);
        sb.append(", maxIdle=").append(maxIdle);
        sb.append(", host='").append(host).append('\'');
        sb.append(", port=").append(port);
        sb.append(", auth=").append(auth);
        sb.append(", password='").append(password).append('\'');
        sb.append(", timeout=").append(timeout);
        sb.append(", maxWait=").append(maxWait);
        sb.append(", testOnBorrow=").append(testOnBorrow);
        sb.append(", serializableType=").append(serializableType);
        sb.append('}');
        return sb.toString();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public RedisProperties setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
        return this;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public RedisProperties setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
        return this;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public RedisProperties setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
        return this;
    }

    public String getHost() {
        return host;
    }

    public RedisProperties setHost(String host) {
        this.host = host;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public RedisProperties setPort(Integer port) {
        this.port = port;
        return this;
    }

    public Boolean isAuth() {
        return auth;
    }

    public RedisProperties setAuth(Boolean auth) {
        this.auth = auth;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RedisProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public RedisProperties setTimeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    public Integer getMaxWait() {
        return maxWait;
    }

    public RedisProperties setMaxWait(Integer maxWait) {
        this.maxWait = maxWait;
        return this;
    }

    public Boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public RedisProperties setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
        return this;
    }

    public RedisSerializableEnum getSerializableType() {
        return serializableType;
    }

    public RedisProperties setSerializableType(RedisSerializableEnum serializableType) {
        this.serializableType = serializableType;
        return this;
    }

    public void init() {
        maxTotal = null == maxTotal ? DefaultProperties.DEFAULT_MAX_TOTAL : maxTotal;
        minIdle = null == minIdle ? DefaultProperties.DEFAULT_MIN_IDLE : minIdle;
        maxIdle = null == maxIdle ? DefaultProperties.DEFAULT_MAX_IDLE : maxIdle;
        port = null == port ? DefaultProperties.DEFAULT_PORT : port;
        auth = null == auth ? DefaultProperties.DEFAULT_AUTH : auth;
        timeout = null == timeout ? DefaultProperties.DEFAULT_TIMEOUT : timeout;
        maxWait = null == maxWait ? DefaultProperties.DEFAULT_MAX_WAIT : maxWait;
        testOnBorrow = null == testOnBorrow ? DefaultProperties.DEFAULT_TEST_ON_BORROW : testOnBorrow;
    }

    private static class DefaultProperties {
        /**
         * 最大连接数
         */
        private static final Integer DEFAULT_MAX_TOTAL = 50;
        /**
         * 最小空余数
         */
        private static final Integer DEFAULT_MIN_IDLE = 10;
        /**
         * 最大空余数
         */
        private static final Integer DEFAULT_MAX_IDLE = 30;
        /**
         * 端口
         */
        private static final Integer DEFAULT_PORT = 6379;
        /**
         * 是否需要授权
         */
        private static final Boolean DEFAULT_AUTH = false;
        /**
         * 超时时间 单位毫秒
         */
        private static final Integer DEFAULT_TIMEOUT = 10000;
        /**
         * 等待可用连接的最大时间，单位毫秒
         */
        private static final Integer DEFAULT_MAX_WAIT = 10000;
        /**
         * 获取jedis客户端时是否验证客户端可用
         */
        private static final Boolean DEFAULT_TEST_ON_BORROW = true;
    }
}
