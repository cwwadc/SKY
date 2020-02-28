package com.sky.core.service.api.route;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import com.sky.service.api.struct.domain.common.RMIBinding;

/**
 * RMI协议路由适配器组件，封装RMI协议下的消息发送，实现路由适配器接口，目前主要作为内部服务调用使用
 * 
 */
public class RMIRouteAware extends ApiRouteAware<String, Object> {
	private static final Logger logger = LoggerFactory.getLogger(RMIRouteAware.class);
	
	private RmiProxyFactoryBean rmiProxy;
	private RMIBinding binding;
	
	public RMIRouteAware(RMIBinding binding) {
		this.binding = binding;
		rmiProxy = new RmiProxyFactoryBean();
		rmiProxy.setServiceUrl(binding.getServiceUrl());
		rmiProxy.setServiceInterface(binding.getServiceClass());
		rmiProxy.setRefreshStubOnConnectFailure(true);
		rmiProxy.setCacheStub(true);
		rmiProxy.setLookupStubOnStartup(false);
		rmiProxy.afterPropertiesSet();
	}

	@Override
	public Object deliver(String deliverContent, String useDataCompress) {
		Object result = null;
		try {
			Object o = rmiProxy.getObject();
			Method method = this.binding.getServiceClass().getMethod(this.binding.getServiceMethod(), String.class);
			result = method.invoke(o, deliverContent);
		} catch (Exception e) {
			logger.error("rmi service invoke error", e);
		}
		return result;
	}

	@Override
	public Boolean isRemoteEndReachable() {
		// 默认远端服务可达
		return Boolean.TRUE;
	}
	
}
