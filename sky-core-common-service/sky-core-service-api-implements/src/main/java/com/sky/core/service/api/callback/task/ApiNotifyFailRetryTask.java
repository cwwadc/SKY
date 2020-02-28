package com.sky.core.service.api.callback.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.base.lang.time.DateTimeUtils;
import com.sky.core.service.api.route.ApiFailRetryManager;
import com.sky.core.service.api.route.ApiRouteAware;
import com.sky.service.api.struct.context.FailRetryableApiContext;

public class ApiNotifyFailRetryTask implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ApiNotifyFailRetryTask.class);
	
	private FailRetryableApiContext<String> apiContext;
	private ApiRouteAware<String, Object> apiRouteAware;
	private ApiFailRetryManager apiFailRetryManager;
	
	public ApiNotifyFailRetryTask(FailRetryableApiContext<String> apiContext, ApiRouteAware<String, Object> apiRouteAware, ApiFailRetryManager apiFailRetryManager) {
		this.apiContext = apiContext;
		this.apiRouteAware = apiRouteAware;
		this.apiFailRetryManager = apiFailRetryManager;
	}

	@Override
	public void run() {
		boolean retryComplete = false;
		logger.info("api-notify-retry task start, trace-id -> {}", apiContext.getTraceId());
		if(apiRouteAware.isRemoteEndReachable()) {
			try {
				apiRouteAware.deliver(apiContext.getRouteMsg(), apiContext.getUseDataCompress());
				retryComplete = true;
			} catch (Exception e) {
				logger.error("api-notify-retry failed, trace-id -> {}", apiContext.getTraceId());
			}
		}
		//重试不成功执行以下逻辑：
		//取得下次重试时间
		//若下次重试时间在重试截止时间之内，则继续注册重试任务
		if(retryComplete) {
			apiFailRetryManager.updateRetryApiContext(apiContext, true);
			logger.info("api-notify-retry task run complete, trace-id -> {}", apiContext.getTraceId());
		} else {
			if(DateTimeUtils.currentTimeMillis() < apiContext.getRetryExpireTime()) {
				apiFailRetryManager.updateRetryApiContext(apiContext, false);
				apiFailRetryManager.registerFailRetryTask(this);
				logger.info("api-notify-retry task reenter, trace-id -> {}, next time to run -> {}.", apiContext.getTraceId(), DateTimeUtils.format(apiContext.getNextTime(), "yyyy-MM-dd HH:mm:ss"));
			} else {
				logger.info("api-notify-retry task didnot complete but expired, exit retry queue, trace-id -> {}", apiContext.getTraceId());
			} 
		}
	}

	public FailRetryableApiContext<String> getApiContext() {
		return apiContext;
	}

	public void setApiContext(FailRetryableApiContext<String> apiContext) {
		this.apiContext = apiContext;
	}

	public ApiRouteAware<String, Object> getApiRouteAware() {
		return apiRouteAware;
	}

	public void setApiRouteAware(ApiRouteAware<String, Object> apiRouteAware) {
		this.apiRouteAware = apiRouteAware;
	}

	public ApiFailRetryManager getApiFailRetryManager() {
		return apiFailRetryManager;
	}

	public void setApiFailRetryManager(ApiFailRetryManager apiFailRetryManager) {
		this.apiFailRetryManager = apiFailRetryManager;
	}

}
