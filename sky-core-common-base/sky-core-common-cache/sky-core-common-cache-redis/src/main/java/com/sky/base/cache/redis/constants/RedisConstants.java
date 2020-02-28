package com.sky.base.cache.redis.constants;

/**
 * @author zzq
 * @version v1.0
 * @Title com.ursa.base.cache.redis.constants.RedisConstants
 * @Description redis 常量类
 * @date 2018/12/11
 */
public class RedisConstants {

	private RedisConstants() {
		throw new IllegalStateException("constants class");
	}

	/**
	 * 设置成功
	 */
	public static final String S_OK = "OK";

	/**
	 * 设置成功
	 */
	public static final Long L_OK = 1L;

	/**
	 * NX -- Only set the key if it does not already exist.
	 */
	public static final String SET_IF_NOT_EXIST = "NX";

	/**
	 * XX -- Only set the key if it already exist.
	 */
	public static final String SET_IF_EXIST = "XX";

	/**
	 * EX seconds -- Set the specified expire time, in seconds.
	 */
	public static final String SET_WITH_EXPIRE_TIME_SECONDS = "EX";

	/**
	 * PX milliseconds -- Set the specified expire time, in milliseconds.
	 */
	public static final String SET_WITH_EXPIRE_TIME_MILLISECONDS = "PX";

}
