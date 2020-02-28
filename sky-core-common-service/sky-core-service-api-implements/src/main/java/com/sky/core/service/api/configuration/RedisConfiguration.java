package com.sky.core.service.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sky.base.cache.redis.client.RedisClient;
import com.sky.base.cache.redis.client.impl.RedisClientImpl;
import com.sky.base.cache.redis.config.RedisProperties;
import com.sky.base.lang.string.StringUtils;

/**
 * redis操作配置bean
 *
 */
@Configuration
public class RedisConfiguration {
	
	@Bean
    public RedisClient skyRedisClient(@Value("${redis.host}") String redisHost, @Value("${redis.password}") String password) {
        RedisProperties redisProperties = new RedisProperties();
        String[] hostAndPort = redisHost.split(":");
        redisProperties.setHost(hostAndPort[0]);
        redisProperties.setPort(Integer.valueOf(hostAndPort[1]));
        if (StringUtils.isBlank(password)) {
            redisProperties.setAuth(false);
            redisProperties.setPassword(null);
        } else {
            redisProperties.setAuth(true);
            redisProperties.setPassword(password);
        }
        redisProperties.init();
        return new RedisClientImpl(redisProperties);
    }
}
