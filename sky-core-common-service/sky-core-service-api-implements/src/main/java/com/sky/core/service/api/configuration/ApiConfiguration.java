package com.sky.core.service.api.configuration;

import java.util.List;
import java.util.concurrent.Executor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.sky.base.context.configuration.SpringContextConfiguration;
import com.sky.base.context.spring.SpringContext;
import com.sky.core.service.api.callback.ApiCallbackCouplerManager;
import com.sky.core.service.api.callback.endpoint.ApiEndpointManager;
import com.sky.core.service.api.callback.endpoint.RedisBasedApiEndPointManager;
import com.sky.core.service.api.event.ApiEventManger;
import com.sky.core.service.api.manage.ApiRelayManager;
import com.sky.core.service.api.manage.ApiRelayManagerImpl;
import com.sky.core.service.api.route.ApiFailRetryManager;
import com.sky.core.service.api.route.ApiRouteManager;
import com.sky.core.service.api.route.WSRouteAware;
import com.sky.core.service.api.serialize.ApiSerializer;
import com.sky.core.service.api.serialize.XmlBasedApiSerializer;
import com.sky.service.api.event.ApiEventListener;
import com.sky.service.api.struct.domain.SkyMsg;
import com.sky.service.api.struct.domain.common.WSBinding;
import com.sky.service.api.support.ApiContextSupport;

/**
  * 系统接口配置bean
 *
 */
@Configuration
@Import({
	ApiDataSourceConfiguration.class,
	RabbitConfiguration.class,
	RedisConfiguration.class,
	ExecutorConfiguration.class,
	SpringContextConfiguration.class
})
public class ApiConfiguration {
	
	@Value("${sky.api.binding.ws.notify.service-url}")
	private String asyncNotifyServiceUrl;                  //默认接口异步通知web-service配置地址
	@Value("${sky.api.binding.ws.notify.method}")
	private String asyncNotifyMethod;                      //默认接口异步通知web-service调用方法名
	@Value("${sky.api.binding.ws.notify.access-param}")
	private String accessParameters;                       //默认前置访问参数，多个参数以逗号","分隔
	@Value("${sky.api.binding.ws.notify.connect-timeout}")
	private Long notifyConnectTimeout;                     //默认接口异步通知连接超时时间（毫秒）
	@Value("${sky.api.binding.ws.notify.receive-timeout}")
	private Long notifyReceiveTimeout;                     //默认接口异步通知返回读取超时时间（毫秒）
	@Value("${sky.api.binding.ws.notify.max-client}")
	private Integer maxNotifyClient;                       //默认接口异步通知web-service客户端可用数量
	
	/**
	 * @return 默认接口异步通知绑定配置
	 */
	@Bean
	public WSBinding asyncNotifyBinding() {
		String[] accessParamArray = null;
		if(!StringUtils.isEmpty(accessParameters)) {
			accessParamArray = StringUtils.splitPreserveAllTokens(accessParameters, ",");
			return new WSBinding(asyncNotifyServiceUrl, asyncNotifyMethod, notifyConnectTimeout, notifyReceiveTimeout, accessParamArray);
		} else {
			return new WSBinding(asyncNotifyServiceUrl, asyncNotifyMethod, notifyConnectTimeout, notifyReceiveTimeout);
		}
	}
	
	/**
	 * @param asyncNotifyBinding 接口异步通知绑定配置
	 * @return 默认接口异步通知发送适配器（WS协议实现）
	 */
	@Bean
	public WSRouteAware asyncNotifyAware(@Qualifier("asyncNotifyBinding") WSBinding asyncNotifyBinding) {
		return new WSRouteAware(asyncNotifyBinding, maxNotifyClient);
	}
	
	@Bean 
	public ApiContextSupport apiContextSupport() throws Exception {
		ApiContextSupport contextSupport = new ApiContextSupport();
		SpringContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(contextSupport);
		contextSupport.afterPropertiesSet();
		return contextSupport;
	}
	
	@Bean 
	public ApiRouteManager apiRouteManager() throws Exception {
		ApiRouteManager apiRouteManager = new ApiRouteManager();
		SpringContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(apiRouteManager);
		apiRouteManager.afterPropertiesSet();
		return apiRouteManager;
	}
	
	@Bean
	@SuppressWarnings("rawtypes")
	public ApiRelayManager apiRelayManager() {
		ApiRelayManager apiRelayManager = new ApiRelayManagerImpl();
		SpringContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(apiRelayManager);
		return apiRelayManager;
	}
	
	@Bean
	public ApiEndpointManager apiEndpointManager() {
		RedisBasedApiEndPointManager  apiEndpointManager = new RedisBasedApiEndPointManager();
		SpringContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(apiEndpointManager);
		return apiEndpointManager;
	}
	
	@Bean
	public ApiCallbackCouplerManager apiCallbackCouplerManager() throws Exception {
		ApiCallbackCouplerManager apiCallbackCouplerManager = new ApiCallbackCouplerManager();
		SpringContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(apiCallbackCouplerManager);
		apiCallbackCouplerManager.afterPropertiesSet();
		return apiCallbackCouplerManager;
	}
	
	@Bean
	public ApiFailRetryManager apiFailRetryManager() throws Exception {
		ApiFailRetryManager apiFailRetryManager = new ApiFailRetryManager();
		SpringContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(apiFailRetryManager);
		apiFailRetryManager.afterPropertiesSet();
		return apiFailRetryManager;
	}
	
	@Bean 
	public ApiEventManger apiEventManager(@Qualifier("apiEventListeners") List<ApiEventListener> registerListeners,
			@Qualifier("asyncEventBusPool") Executor executor) throws Exception{
		ApiEventManger apiEventManager = new ApiEventManger(registerListeners, executor);
		//apiEventManager.afterPropertiesSet();
		
		return apiEventManager;
	}
	
	@Bean
	public ApiSerializer<SkyMsg> apiSerializer() throws Exception{
		XmlBasedApiSerializer apiSerializer = new XmlBasedApiSerializer();
		apiSerializer.afterPropertiesSet();
		return apiSerializer;
	}
	
}
