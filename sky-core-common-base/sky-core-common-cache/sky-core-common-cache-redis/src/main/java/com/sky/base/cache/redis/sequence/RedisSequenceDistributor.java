package com.sky.base.cache.redis.sequence;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.sky.base.cache.redis.client.RedisClient;
import com.sky.base.context.sequence.SequenceDistributor;

/**
 * 
 * @Title redis序列号派发器实现
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-03-09
 */
public class RedisSequenceDistributor implements SequenceDistributor {

	private RedisClient redisClient;

	private final Set<String> alreadyInitNames = Collections.synchronizedSet(new HashSet<String>());

	public RedisSequenceDistributor(RedisClient redisClient) {
		super();
		this.redisClient = redisClient;
	}

	@Override
	public Long current(String name) {
		if (!alreadyInitNames.contains(name)) {
			redisClient.setnx(name, 0L);
		}
		return redisClient.get(name, Long.class);
	}

	@Override
	public Long next(String name) {
		return Long.valueOf(redisClient.incr(name));
	}

	@Override
	public Long next(String name, int stepWidth) {
		return Long.valueOf(redisClient.incrBy(name, stepWidth));
	}

}
