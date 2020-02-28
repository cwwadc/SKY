package com.sky.core.service.api.configuration;

import java.rmi.RemoteException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

import com.sky.base.context.spring.SpringContext;
import com.sky.core.service.api.callback.endpoint.ApiCallbackEndpoint;
import com.sky.core.service.api.callback.reactor.ApiCallbackReactor;
import com.sky.core.service.api.callback.reactor.ApiCallbackReactorImpl;

/**
  * 接口回调配置bean
 */
@Configuration
public class ApiCallbackConfiguration {
	
	@Value("${sky.api.callback.rmi.registry.port}")
	private Integer callbackRmiRegistryPort;                 //回调接口rmi协议注册端口
	@Value("${sky.api.callback.rmi.serivce.port}")       
	private Integer callbackRmiServicePort;                  //回调接口rmi协议服务端口
	@Value("${sky.api.callback.rmi.service.name}")
	private String callbackRmiServiceName;                   //回调接口rmi协议注册名称
	
	private String callbackServiceClass = "com.sky.core.service.api.callback.reactor.ApiCallbackReactor";   //默认回调接口服务类限定名
	private String callbackMethod = "callback";   //默认回调接口方法名
	
	@Bean
	public ApiCallbackReactor apiCallbackReactor() {
		ApiCallbackReactor apiCallbackReactor = new ApiCallbackReactorImpl();
		SpringContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(apiCallbackReactor);
		return apiCallbackReactor;
	}
	
	/**
	  * 当前应用默认回调端点配置，需指定-Djava.rmi.server.hostname指定回调服务器ip
	 */
	@Bean
	public ApiCallbackEndpoint callbackEndpoint() {
		String registryHost = System.getProperty("java.rmi.server.hostname");
		String serviceUrl = String.format("rmi://%s:%s/%s", registryHost, callbackRmiRegistryPort, callbackRmiServiceName);
		
		ApiCallbackEndpoint endPoint = new ApiCallbackEndpoint(serviceUrl, callbackServiceClass, callbackMethod);
		return endPoint;
	}
	
	/**
	  * 当前应用默认rmi回调服务提供者，使用spring内置RmiServiceExporter注册rmi服务
	 */
	@Bean
    public RmiServiceExporter callbackRmiExporter(ApiCallbackReactor apiCallbackReactor){
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName(callbackRmiServiceName);
        rmiServiceExporter.setService(apiCallbackReactor);
        rmiServiceExporter.setServiceInterface(ApiCallbackReactor.class);
        rmiServiceExporter.setRegistryPort(callbackRmiRegistryPort);
        rmiServiceExporter.setServicePort(callbackRmiServicePort);
        rmiServiceExporter.setAlwaysCreateRegistry(true);
        try {
            rmiServiceExporter.afterPropertiesSet();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return rmiServiceExporter;
    }

}
