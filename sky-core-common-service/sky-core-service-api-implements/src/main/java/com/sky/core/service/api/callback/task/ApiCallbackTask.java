package com.sky.core.service.api.callback.task;

public abstract class ApiCallbackTask {
	private String traceId;
	
	public abstract void onCallback(Object callbackMsg);
	
	public abstract void onCallbackTimeout();

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	
	

}
