package com.sky.core.service.api.event;

import java.util.List;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.sky.service.api.event.ApiEvent;
import com.sky.service.api.event.ApiEventListener;

/**
  * 接口事件管理组件封装，内部维护多个业务事件监听器，事件执行线程池，使用guava-eventbus实现
 *
 */
@Component
public class ApiEventManger implements InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(ApiEventManger.class);
	private static final String DEFAULT_INTERN_BUS_NAME = "LOCAL-API-EVENT-BUS";
	
	private List<ApiEventListener> apiEventListeners;

	private Executor eventBusExecutor;                         //默认事件总线处理线程池，线程池的配置影响并发事件的处理
	
	private EventBus localEventBus;

	public ApiEventManger() {
		super();
	}
	
	public ApiEventManger(List<ApiEventListener> apiEventListeners, Executor eventBusExecutor) {
		super();
		this.apiEventListeners = apiEventListeners;
		this.eventBusExecutor = eventBusExecutor;
	}

	public void register(ApiEventListener listener) {
		localEventBus.register(listener);
	}
	
	public void unRegister(ApiEventListener listener) {
		localEventBus.unregister(listener);
	}
	
	public void postEvent(ApiEvent event) {
		localEventBus.post(event);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initialize();
	}

	protected void initialize() {
		localEventBus = new AsyncEventBus(DEFAULT_INTERN_BUS_NAME, eventBusExecutor);
		for (ApiEventListener listener : apiEventListeners) {
			register(listener);
		}
		logger.info("{} initialize finish", localEventBus.identifier());
	}
}
