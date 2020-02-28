package com.sky.core.service.api.callback;

import com.sky.core.service.api.callback.task.ApiCallbackTask;
import com.sky.service.api.support.ApiContext;

/**
  *  接口回调聚合信息：包装同步/异步接口回调所需要的匹配信息和操作组件
 *  couplerId：回调内容匹配标识
 *  callbackContext：回调接口上下文对象
 *  replyAdapter：当前回调信息默认应答组件
 */
public class ApiCallbackCoupler {
	
	private String couplerId;
	private ApiContext routeContext;
	private ApiCallbackTask callbackTask;
//	private ApiReplyChannelAdapter replyAdapter;
	
	public ApiCallbackCoupler() {
		super();
	}

	public ApiCallbackCoupler(String couplerId, ApiContext routeContext, ApiCallbackTask callbackTask) {
		super();
		this.couplerId = couplerId;
		this.routeContext = routeContext;
		this.callbackTask = callbackTask;
	}

	public String getCouplerId() {
		return couplerId;
	}

	public void setCouplerId(String couplerId) {
		this.couplerId = couplerId;
	}

	public ApiContext getRouteContext() {
		return routeContext;
	}

	public void setRouteContext(ApiContext routeContext) {
		this.routeContext = routeContext;
	}

	public ApiCallbackTask getCallbackTask() {
		return callbackTask;
	}

	public void setCallbackTask(ApiCallbackTask callbackTask) {
		this.callbackTask = callbackTask;
	}
	
}
