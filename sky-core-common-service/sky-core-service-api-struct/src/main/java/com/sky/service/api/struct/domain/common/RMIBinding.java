package com.sky.service.api.struct.domain.common;

public class RMIBinding {
	private String serviceUrl;
	private Class<?> serviceClass;
	private String serviceMethod;
	
	public RMIBinding() {
		super();
	}
	public RMIBinding(String serviceUrl, Class<?> serviceClass, String serviceMethod) {
		super();
		this.serviceUrl = serviceUrl;
		this.serviceClass = serviceClass;
		this.serviceMethod = serviceMethod;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public Class<?> getServiceClass() {
		return serviceClass;
	}
	public void setServiceClass(Class<?> serviceClass) {
		this.serviceClass = serviceClass;
	}
	public String getServiceMethod() {
		return serviceMethod;
	}
	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}
	
	
}
