package com.sky.core.service.api.event.http;

import com.sky.core.service.api.callback.ApiCallbackCouplerManager;
import com.sky.service.api.event.ContextualApiEvent;

/**
  * 回调事件定义
 *
 */
public class CallbackEvent extends ContextualApiEvent {
	
	private ApiCallbackCouplerManager apiCallbackCouplerManager;
	private String responseMetadata;

	public ApiCallbackCouplerManager getApiCallbackCouplerManager() {
		return apiCallbackCouplerManager;
	}

	public void setApiCallbackCouplerManager(ApiCallbackCouplerManager apiCallbackCouplerManager) {
		this.apiCallbackCouplerManager = apiCallbackCouplerManager;
	}

	public String getResponseMetadata() {
		return responseMetadata;
	}

	public void setResponseMetadata(String responseMetadata) {
		this.responseMetadata = responseMetadata;
	}

	public CallbackEvent(String responseMetadata) {
		super();
		this.responseMetadata = responseMetadata;
	}

	public CallbackEvent() {
		super();
	}
	
	

}
