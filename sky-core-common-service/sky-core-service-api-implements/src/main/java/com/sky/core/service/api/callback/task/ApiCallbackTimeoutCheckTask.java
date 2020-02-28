package com.sky.core.service.api.callback.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.core.service.api.callback.ApiCallbackCoupler;
import com.sky.core.service.api.callback.ApiCallbackCouplerManager;
import com.sky.core.service.api.event.ApiEventManger;
import com.sky.core.service.api.event.http.CallbackTimeoutEvent;
import com.sky.service.api.struct.context.RoutableApiContext;
import com.sky.service.api.support.ApiContext;

/**
  * 接口超时任务封装：内部维护一个接口上下文以及事件管理组件
 *
 */
public class ApiCallbackTimeoutCheckTask implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ApiCallbackTimeoutCheckTask.class);
	private ApiContext apiContext;
	private ApiEventManger apiEventManager;
	private ApiCallbackCouplerManager callbackCouplerManager;
	
	public ApiCallbackTimeoutCheckTask() {}
	public ApiCallbackTimeoutCheckTask(ApiContext apiContext, ApiEventManger apiEventManager,
			ApiCallbackCouplerManager callbackCouplerManager) {
		super();
		this.apiContext = apiContext;
		this.apiEventManager = apiEventManager;
		this.callbackCouplerManager = callbackCouplerManager;
	}

	/* 
	  * 默认超时任务实现：触发一个等待超时事件，事件对象包含超时的接口请求上下文、应答对象
	 */
	@Override
	public void run() {
		logger.debug("api callback-timeout check, trace-id -> {}", apiContext.getTraceId());
		RoutableApiContext<?> routableContext = (RoutableApiContext<?>) apiContext;
		ApiCallbackCoupler coupler = callbackCouplerManager.matchCallbackCoupler(routableContext.getCouplerId());
		if(coupler != null) {
			logger.info("fire callback-timeout event, trace -> id {}", apiContext.getTraceId());
			apiEventManager.postEvent(new CallbackTimeoutEvent(coupler.getCallbackTask()));
		}
	}

}
