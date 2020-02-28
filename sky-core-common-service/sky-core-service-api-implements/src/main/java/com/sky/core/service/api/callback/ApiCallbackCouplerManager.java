package com.sky.core.service.api.callback;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sky.base.lang.task.DelayedTask;
import com.sky.base.lang.task.DelayedTaskManager;
import com.sky.core.service.api.callback.endpoint.ApiCallbackEndpoint;
import com.sky.core.service.api.callback.endpoint.ApiEndpointManager;
import com.sky.core.service.api.callback.task.ApiCallbackTimeoutCheckTask;
import com.sky.core.service.api.event.ApiEventManger;

/**
  *   接口回调聚合管理组件封装：包装回调聚合对象注册、回调聚合对象匹配、回调端点移除方法等
 *
 */
@Component
public class ApiCallbackCouplerManager implements InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(ApiCallbackCouplerManager.class);
	
	private Map<String, ApiCallbackCoupler> callbackCouplerMap;
	private DelayedTaskManager apiAutoTimeoutTaskManager;
	@Autowired
	private ApiEventManger apiEventManager;
	@Autowired
	private ApiEndpointManager apiEndPointManager;
	
	/**
	  * 回调聚合管理组件默认初始化方法：内部维护一个线程安全且无界的回调聚合对象列表，以及一个含单一任务执行线程的超时任务管理组件  
	 */
	public void initialize() {
		callbackCouplerMap = new ConcurrentHashMap<String, ApiCallbackCoupler>();
		ThreadFactory apiAutoTimeoutThreadFactory = new ThreadFactoryBuilder().setNameFormat("api-timeout-observer-%d").build();
		apiAutoTimeoutTaskManager = new DelayedTaskManager("api-timeout-manager", Executors.newSingleThreadExecutor(apiAutoTimeoutThreadFactory));
		apiAutoTimeoutTaskManager.initialize();
	}
	
	/** 回调聚合对象匹配方法
	 * @param couplerId 匹配标识，该标识作为异步接口场景中响应上下文与请求上下文进行关联匹配的唯一标识
	 * @return 返回当前系统匹配到的请求上下文回调聚合对象，若未空则指示不存在对应的请求上下文或请求上下文已失效（若指定超时时间）
	 */
	public ApiCallbackCoupler matchCallbackCoupler(String couplerId) {
		return callbackCouplerMap.remove(couplerId);
	}
	
	/** 请求上下文回调聚合对象注册方法
	 * @param callbackCoupler 请求上下文回调聚合对象
	 * @param callbackEndpoint 回调端点信息
	 * @param callbackWaitTimeout 回调等待超时时间（秒）
	 * @param timeoutTask 回调等待超时任务
	 */
	public void registerCallbackCoupler(ApiCallbackCoupler callbackCoupler, ApiCallbackEndpoint callbackEndpoint, 
			int callbackWaitTimeout) {
		//加入回调等待列表
		callbackCouplerMap.put(callbackCoupler.getCouplerId(), callbackCoupler);
		apiEndPointManager.registerCallbackEndpoint(callbackCoupler.getCouplerId(), callbackEndpoint, callbackWaitTimeout);
		//注册超时监听任务
		ApiCallbackTimeoutCheckTask callbackTimeoutCheckTask = new ApiCallbackTimeoutCheckTask(callbackCoupler.getRouteContext(), this.apiEventManager, this);
		DelayedTask<ApiCallbackTimeoutCheckTask> delayedTask = new DelayedTask<ApiCallbackTimeoutCheckTask>(callbackCoupler.getCouplerId(), 
				callbackTimeoutCheckTask, callbackWaitTimeout, TimeUnit.SECONDS);
		apiAutoTimeoutTaskManager.register(delayedTask);
	}
	
	/** 请求上下文回调聚合对象注销方法
	 * @param couplerId
	 */
	public void unRegisterCallbackCoupler(String couplerId) {
		callbackCouplerMap.remove(couplerId);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initialize();
	}
	

}
