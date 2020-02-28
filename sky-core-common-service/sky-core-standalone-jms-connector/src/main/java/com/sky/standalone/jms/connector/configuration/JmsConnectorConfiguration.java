
package com.sky.standalone.jms.connector.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.sky.core.service.api.callback.endpoint.ApiCallbackEndpoint;
import com.sky.core.service.api.configuration.MQApiConfiguration;
import com.sky.standalone.container.node.Node;
import com.sky.standalone.jms.connector.node.JmsConnectorNode;
import com.sky.standalone.jms.connector.webspheremq.MQApiListenerContainer;

@Configuration
@Import({
		  MQApiConfiguration.class
})
public class JmsConnectorConfiguration {
	
	@Value("${sky.api.callback.rmi.registry.port}")
	private Integer callbackRmiRegistryPort;                 //回调接口rmi协议注册端口
	@Value("${sky.api.callback.rmi.serivce.port}")       
	private Integer callbackRmiServicePort;                  //回调接口rmi协议服务端口
	@Value("${sky.api.callback.rmi.service.name}")
	private String callbackRmiServiceName;                   //回调接口rmi协议注册名称
	
	private String callbackServiceClass = "com.sky.core.service.api.callback.ApiCallbackReactor";   //默认回调接口服务类限定名
	private String callbackMethod = "callback";   //默认回调接口方法名
	
    @Bean
    public Node node() {
        return new JmsConnectorNode();
    }
    
    @Bean
    public MQApiListenerContainer mqApiListenerContainer() {
    	return new MQApiListenerContainer();
    }	
    
//    @Bean
//    //@Scope("prototype")
//    public MQApiProxy mqApiProxy() {
//    	return new MQApiProxy();
//    }
    
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
   
}
