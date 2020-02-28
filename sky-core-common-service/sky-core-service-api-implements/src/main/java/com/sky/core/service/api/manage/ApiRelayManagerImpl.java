package com.sky.core.service.api.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.core.service.api.callback.ApiCallbackCoupler;
import com.sky.core.service.api.callback.ApiCallbackCouplerManager;
import com.sky.core.service.api.callback.endpoint.ApiCallbackEndpoint;
import com.sky.core.service.api.callback.task.ApiCallbackTask;
import com.sky.core.service.api.route.ApiRouteAware;
import com.sky.service.api.struct.context.FailRetryableApiContext;
import com.sky.service.api.struct.context.RoutableApiContext;

@Component
public class ApiRelayManagerImpl implements ApiRelayManager<String, Object> {
	private static final Logger logger = LoggerFactory.getLogger(ApiRelayManagerImpl.class);
	
	@Autowired
	private ApiCallbackCouplerManager ApiCallbackCouplerManager;
	@Autowired
	private ApiCallbackEndpoint apiCallbackEndpoint;

	@Override
	public void commit(FailRetryableApiContext<String> context, ApiRouteAware<String, Object> aware, 
			ApiCallbackTask callbackTask, Integer callbackTimeout) {
		if(aware.getSyncReturn()) {
			sendAndReturnImmediate(context, aware, callbackTask);
		} else {
			sendAndReturnReactive(context, aware, callbackTask, callbackTimeout);
		}
	}

	protected void sendAndReturnReactive(FailRetryableApiContext<String> context, ApiRouteAware<String, Object> aware, 
			ApiCallbackTask callbackTask, int callbackTimeout) {
		
		//1、转发消息
		aware.deliver(context.getRouteMsg(), context.getUseDataCompress());
		logger.debug("deliver message finish, trace-id -> {}", context.getTraceId());
		
		//2、注册回调处理对象、回调超时处理任务，本线程立即返回，由回调线程池处理回调方法
		ApiCallbackCoupler callbackCoupler = new ApiCallbackCoupler(context.getCouplerId(), context, callbackTask);
		ApiCallbackCouplerManager.registerCallbackCoupler(callbackCoupler, apiCallbackEndpoint, callbackTimeout);
		logger.debug("register callback-coupler finish and wait, trace-id -> {}", context.getTraceId());
	}

	protected void sendAndReturnImmediate(FailRetryableApiContext<String> context, ApiRouteAware<String, Object> aware, ApiCallbackTask callbackTask) {
		try {
			//1、转发消息并同步取得相应
			Object result = aware.deliver(context.getRouteMsg(), context.getUseDataCompress());
			String returnMessage = String.valueOf(result);
			
			//2、调用同步回调处理方法，由当前线程执行
			callbackTask.onCallback(returnMessage);
		} catch (Exception e) {
			logger.error("send-and-return-immediate deliver error", e);
			if(e.getMessage().contains("timeout")) {
				callbackTask.onCallbackTimeout();
			}
		}
	}

	@Override
	public void commit(FailRetryableApiContext<String> context, ApiRouteAware<String, Object> aware) {
		//1、转发消息并立即返回
		aware.deliver(context.getRouteMsg(), context.getUseDataCompress());
		logger.debug("deliver message finish, trace-id -> {}", context.getTraceId());
	}
	

}
