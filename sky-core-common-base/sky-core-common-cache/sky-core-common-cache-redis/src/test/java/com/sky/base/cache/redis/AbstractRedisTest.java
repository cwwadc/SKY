package com.sky.base.cache.redis;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.sky.base.cache.redis.client.RedisClient;
import com.sky.base.cache.redis.client.impl.RedisClientImpl;
import com.sky.base.cache.redis.config.RedisProperties;
import com.sky.base.test.net.NetUtils;

import redis.embedded.RedisServer;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-18
 */
public abstract class AbstractRedisTest {

	protected static RedisServer redisServer;

	protected static RedisClient redisClient;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		int port = NetUtils.getAvailablePort();
		redisServer = new RedisServer(port);
		redisServer.start();
		RedisProperties properties = new RedisProperties();
		properties.setHost("localhost");
		properties.setPort(port);
		redisClient = new RedisClientImpl(properties);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		redisServer.stop();
	}
}
