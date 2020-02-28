package com.sky.core.service.api.callback.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.base.cache.redis.client.RedisClient;
import com.sky.base.serialize.json.JsonUtils;
import com.sky.service.api.exception.ApiException;

/**
  * 基于redis模式实现的回调端点管理组件，内部维护一个redis操作客户端
 *
 */
@Component
public class RedisBasedApiEndPointManager implements ApiEndpointManager {
	private static final Logger logger = LoggerFactory.getLogger(RedisBasedApiEndPointManager.class);
	
	@Autowired
	private RedisClient redisClient;
	
	/** 注册回调地址
	 * @param endpointKey 回调地址注册键
	 * @param endpoint 回调地址
	 * @param timeout 回调地址保存超时时间-Timeunit.seconds(秒)
	 */
	@Override
	public void registerCallbackEndpoint(String endpointKey, ApiCallbackEndpoint endpoint, int timeoutSeconds) {
		
		if (redisClient.setex(endpointKey, endpoint, timeoutSeconds)) {
			logger.debug(
					"register callback endpoint success, endpoint-key -> {}, endpoint -> {}, timeout-seconds -> {}",
					endpointKey, JsonUtils.toJsonString(endpoint), timeoutSeconds);
		} else {
			throw new ApiException(String.format(
					"register callback endpoint fail, endpoint-key -> {}, endpoint -> {}, timeout-seconds -> {}",
					endpointKey, JsonUtils.toJsonString(endpoint), timeoutSeconds));
		}
	}
	
	/** 检索回调地址
	 * @param endpointKey 回调地址注册键
	 * @return
	 */
	@Override
	public ApiCallbackEndpoint findCallbackEndpoint(String endpointKey) {
		return redisClient.get(endpointKey, ApiCallbackEndpoint.class);
	}
	
	/** 移除回调地址
	 * @param endpointKey
	 * @return 
	 */
	@Override
	public ApiCallbackEndpoint unRegisterCallbackEndpoint(String endpointKey) {
		ApiCallbackEndpoint endpoint = findCallbackEndpoint(endpointKey);
		redisClient.del(endpointKey);
		return endpoint;
	}
}
