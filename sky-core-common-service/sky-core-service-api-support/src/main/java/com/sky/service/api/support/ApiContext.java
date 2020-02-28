package com.sky.service.api.support;

import java.io.Serializable;

import com.sky.service.api.support.utils.ApiMsgUtils;

public class ApiContext implements Serializable {
	private static final long serialVersionUID = 5785636688300089240L;
	private String appName;
	private String traceId;
	private Class<?>[] skippedPlugins;
	
	public ApiContext() {
		setTraceId(ApiMsgUtils.newTraceId(appName));
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	public Class<?>[] getSkippedPlugins() {
		return skippedPlugins;
	}
	public void setSkippedPlugins(Class<?>[] skippedPlugins) {
		this.skippedPlugins = skippedPlugins;
	}
	
}
