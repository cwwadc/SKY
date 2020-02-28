package com.sky.core.service.api.route;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sky.base.lang.task.DelayedTask;
import com.sky.base.lang.task.DelayedTaskManager;
import com.sky.base.lang.time.DateTimeUtils;
import com.sky.core.service.api.callback.task.ApiNotifyFailRetryTask;
import com.sky.service.api.struct.context.FailRetryableApiContext;

@Component
public class ApiFailRetryManager implements InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(ApiFailRetryManager.class);
	
	private DelayedTaskManager apiFailRetryTaskManager;
	@Autowired
	@Qualifier("apiFailRetryPool")
	private Executor apiFailRetryTaskExecutor;
	
	/**
	  * 失败重试任务管理组件默认初始化方法：内部维护一个重试任务执行线程组，以及一个延迟执行的重试任务管理组件
	 */
	public void initialize() {
		apiFailRetryTaskManager = new DelayedTaskManager("api-retryctl-manager", apiFailRetryTaskExecutor);
		apiFailRetryTaskManager.initialize();
	}
	
	/**
	  * 注册失败重试任务
	 */
	public void registerFailRetryTask(FailRetryableApiContext<String> failRetryContext, ApiRouteAware<String, Object> apiRouteAware) {
		ApiNotifyFailRetryTask failRetryTask = new ApiNotifyFailRetryTask(failRetryContext, apiRouteAware, this);
		registerFailRetryTask(failRetryTask);
	}
	
	/**
	  * 注册失败重试任务
	 */
	public void registerFailRetryTask(ApiNotifyFailRetryTask failRetryTask) {
		DelayedTask<ApiNotifyFailRetryTask> delayedRetryTask = new DelayedTask<ApiNotifyFailRetryTask>(failRetryTask.getApiContext().getTraceId(),
				failRetryTask, failRetryTask.getApiContext().getFailRetryInterval(), TimeUnit.MINUTES);
		apiFailRetryTaskManager.register(delayedRetryTask);
	}
	
	/** 预处理失败重试参数
	 * @param apiContext
	 */
	public void prepareRetryApiContext(FailRetryableApiContext<?> apiContext) {
		Long retryActivateTime = DateTimeUtils.currentTimeMillis();
		Long nextTime = retryActivateTime + apiContext.getFailRetryInterval() * 60000;
		Long retryExpireTime = retryActivateTime + apiContext.getFailRetryTimeLimit() * 60000;
		
		apiContext.setRetryActivateTime(retryActivateTime);
		apiContext.setNextTime(nextTime);
		apiContext.setRetryExpireTime(retryExpireTime);
		apiContext.setTotalRetryCount(0);
	}
	
	/** 更新失败重试参数（包括下次重试时间、累计重试次数、重试期限、重试间隔等）
	 * @param apiContext
	 */
	public void updateRetryApiContext(FailRetryableApiContext<?> apiContext, boolean retryCompleted) {
		apiContext.setTotalRetryCount(apiContext.getTotalRetryCount().intValue() + 1);
		if(!retryCompleted) {
			Long nextTime = apiContext.getNextTime() + apiContext.getFailRetryInterval() * 60000;
			apiContext.setNextTime(nextTime);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initialize();
		
	}

}
