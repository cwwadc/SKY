package com.sky.service.api.struct.domain.common;

public class WSBinding {
	private String serviceUrl;
	private String method;
	private Long connectTimeout;
	private Long receiveTimeout;
	private String[] accessParameters;    //访问配置参数，该参数作为method的前置入参

	public WSBinding() {
		super();
	}
	public WSBinding(String serviceUrl, String method, Long connectTimeout, Long receiveTimeout, String... accessParameters) {
		super();
		this.serviceUrl = serviceUrl;
		this.method = method;
		this.connectTimeout = connectTimeout;
		this.receiveTimeout = receiveTimeout;
		this.accessParameters = accessParameters;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Long getConnectTimeout() {
		return connectTimeout;
	}
	public void setConnectTimeout(Long connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	public Long getReceiveTimeout() {
		return receiveTimeout;
	}
	public void setReceiveTimeout(Long receiveTimeout) {
		this.receiveTimeout = receiveTimeout;
	}
	public String[] getAccessParameters() {
		return accessParameters;
	}
	public void setAccessParameters(String[] accessParameters) {
		this.accessParameters = accessParameters;
	}
	
}
