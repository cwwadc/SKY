package com.sky.core.service.api.event.http;

import com.sky.core.service.api.callback.task.ApiCallbackTask;
import com.sky.service.api.event.ContextualApiEvent;

/**
 * 回调等待超时事件定义
 *
 */
public class CallbackTimeoutEvent extends ContextualApiEvent {
	private ApiCallbackTask apiCallbackTask;

	public CallbackTimeoutEvent() {
		super();
	}

	public CallbackTimeoutEvent(ApiCallbackTask apiCallbackTask) {
		super();
		this.apiCallbackTask = apiCallbackTask;
	}

	public ApiCallbackTask getApiCallbackTask() {
		return apiCallbackTask;
	}

	public void setApiCallbackTask(ApiCallbackTask apiCallbackTask) {
		this.apiCallbackTask = apiCallbackTask;
	}

}
