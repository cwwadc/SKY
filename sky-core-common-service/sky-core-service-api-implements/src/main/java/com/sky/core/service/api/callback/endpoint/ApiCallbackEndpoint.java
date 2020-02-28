package com.sky.core.service.api.callback.endpoint;

import java.io.Serializable;

/**
  * 回调端点信息封装
 * serviceUrl：回调地址，默认使用rmi实现的服务地址格式，如rmi://host:registry-port/service-name形式
 * serviceClassName：回调接口名称，全限定名格式：如com.sky.core.service.api.callback.ApiCallbackReactor
 * serviceMethod：回调服务方法，方法签名名称：如callback
 */
public class ApiCallbackEndpoint implements Serializable {

	private static final long serialVersionUID = 4205785769975376384L;
	
	private String serviceUrl;
	private String serviceClassName;
	private String serviceMethod;
	
	public ApiCallbackEndpoint() {
		super();
	}
	public ApiCallbackEndpoint(String serviceUrl, String serviceClassName, String serviceMethod) {
		super();
		this.serviceUrl = serviceUrl;
		this.serviceClassName = serviceClassName;
		this.serviceMethod = serviceMethod;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public String getServiceClassName() {
		return serviceClassName;
	}
	public void setServiceClassName(String serviceClassName) {
		this.serviceClassName = serviceClassName;
	}
	public String getServiceMethod() {
		return serviceMethod;
	}
	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}
	
	

	
	
	
}
