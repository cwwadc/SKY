package com.sky.core.service.api.route;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.base.serialize.json.JsonUtils;
import com.sky.core.service.api.utils.JaxWsDynamicClientFactory;
import com.sky.service.api.struct.domain.common.WSBinding;

/**
 * Web-Service协议路由适配器组件，封装Web-Service协议下的消息发送，实现路由适配器接口，目前主要作为主动调用外部系统服务时使用
 *
 */
public class WSRouteAware extends ApiRouteAware<String, Object> {
	private static final Logger logger = LoggerFactory.getLogger(WSRouteAware.class);
	private static final String WS_ROUTE_AWARE_CHARSET = "GBK";
	private static final int DEFAULT_MAX_CLIENT_SIZE = 400;

	protected WSBinding binding;
	protected JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
	protected BlockingQueue<Client> idleClients;
	protected int maxClientSize;
	protected AtomicInteger clientIncreaseCounter = new AtomicInteger(0);
	protected volatile boolean isInitialized = false;

	public WSRouteAware(WSBinding binding, Integer maxClientSize) {
		this.binding = binding;
		this.maxClientSize = maxClientSize == null ? DEFAULT_MAX_CLIENT_SIZE : maxClientSize.intValue();
		if(isRemoteEndReachable()) {
			initialize();
		}
	}

	@Override
	public synchronized void initialize() {    //初始化方法使用同步锁，保证仅由单一线程执行初始化过程
		if(!isInitialized) {
			try {
				this.idleClients = new ArrayBlockingQueue<Client>(this.maxClientSize);
				synchronized(idleClients) {
					while(clientIncreaseCounter.get() < this.maxClientSize) {
						increaseClient();
					}
					isInitialized = true;
				}
				
				logger.info("initialize WSRouteAware success, binding -> {}, current idle ws-client -> {}, clientIncreaseCounter -> {}", 
						JsonUtils.toJsonString(this.binding), this.idleClients.size(), this.clientIncreaseCounter);
			} catch (Exception e) {
				if(this.idleClients != null) {    //初始化中途异常时清除队列已持有的客户端
					this.idleClients = null;
				}
				throw new RuntimeException("WSRouteAware initialize error", e);
			}
		}
	}

	@Override
	public void destory() {
		if(idleClients != null) {
			for (Client client : idleClients) {
				try {
					client.destroy();
				} catch (Exception e) {
				
				}
			}
		}
	}
	
	@Override
	public Object deliver(String deliverContent, String useDataCompress) {
		Object[] result = null;
		Client client = null;
		String returnContent = null;
		try {
			client = getClient();
	        Object[] parameters = null;
	    	if(binding.getAccessParameters() != null) {
	    		parameters = new String[binding.getAccessParameters().length + 1];
	    		System.arraycopy(binding.getAccessParameters(), 0, parameters, 0, binding.getAccessParameters().length);   //放入前置访问参数 
	    	} else {
	    		parameters = new String[1];
	    	}
//        	parameters[parameters.length - 1] = URLEncoder.encode(deliverContent, WS_ROUTE_AWARE_CHARSET);   ////末尾参数设置为转发信息体
			parameters[parameters.length - 1] = deliverContent;
			logger.debug("ws service invoke begin, use client -> {}", client);
        	result = client.invoke(binding.getMethod(), parameters);
    		if(result == null) {
    			logger.error("ws service invoke error, ws service did not return");
    		}
    		
//    		returnContent = URLDecoder.decode(String.valueOf(result[0]), WS_ROUTE_AWARE_CHARSET);
			returnContent = String.valueOf(result[0]);
    		logger.debug("ws service return -> {}", returnContent);
		} catch (Exception e) {
			logger.error("ws service invoke error", e);
			throw new RuntimeException(e);
		} finally {
			releaseClient(client);
		}
		return returnContent;
	}
	
	protected Client getClient() {
		if(!isInitialized) {    //获取客户端前先检查当前适配器组件是否已初始化完成，若未完成则由当前线程执行初始化动作
			initialize();
		}
		
		Client client = null;
		if(idleClients.size() == 0){
			synchronized (idleClients) {
				if(this.clientIncreaseCounter.get() < this.maxClientSize)
					client = increaseClient();
			}
		}
		if(client == null){
			try {
				client = this.idleClients.take();
			} catch (Exception e) {
				logger.error("take WSRouteAware-Client error", e);
			}
		}
		return client;
	}
	
	protected Client increaseClient() {
		try {
			Client client = clientFactory.createClient(binding.getServiceUrl());
			HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();   //自定义调用策略
	        httpClientPolicy.setConnectionTimeout(binding.getConnectTimeout()); 
	        httpClientPolicy.setReceiveTimeout(binding.getReceiveTimeout());
	        httpClientPolicy.setAllowChunking(false);
	        HTTPConduit http = (HTTPConduit) client.getConduit(); 
	        http.setClient(httpClientPolicy);
	        this.idleClients.put(client);
	        clientIncreaseCounter.incrementAndGet();
			return client;
		} catch (Exception e) {
			throw new RuntimeException("increase ws-client error", e);
		}
		
	}

	protected void releaseClient(Client client){
		if(client != null){
			try {
				idleClients.put(client);
			} catch (Exception e) {
				logger.error("release WSRouteAware-Client error", e);
			}
		}
	}

	@Override
	public Boolean isRemoteEndReachable() {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(binding.getServiceUrl());
			HttpURLConnection oc = (HttpURLConnection) url.openConnection();
			oc.setUseCaches(false);
			oc.setConnectTimeout(binding.getConnectTimeout().intValue());	//设置超时时间
			int responseStatus = oc.getResponseCode();  //取得响应状态
			return HttpURLConnection.HTTP_OK == responseStatus;			
		} catch (Exception e) {
			logger.error("remote-end unReachable, service-url -> " + binding.getServiceUrl(), e);
			return Boolean.FALSE;
		} finally {
			if(connection != null) {
				try {
					connection.disconnect();
				} catch (Exception ex) {}
			}
		}
	}

}
