package com.sky.core.service.api.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.sky.base.context.spring.SpringContext;
import com.sky.core.service.api.event.http.DefaultHttpApiEventListener;
import com.sky.core.service.api.transaction.dispatcher.DefaultHttpApiDispatcher;
import com.sky.core.service.api.transaction.handler.DefaultHttpAsyncTransactionHandler;
import com.sky.core.service.api.transaction.handler.DefaultHttpRealtimeTransactionHandler;
import com.sky.core.service.api.transaction.handler.DefaultTransactionErrorHandler;
import com.sky.core.service.api.transaction.plugin.MessageRecordingPlugin;
import com.sky.core.service.api.transaction.plugin.MessageValidateApiPlugin;
import com.sky.service.api.event.ApiEventListener;
import com.sky.service.api.support.ApiDispatcher;

@Configuration
@Import({
	ApiConfiguration.class,
	ApiCallbackConfiguration.class
})
public class HttpApiConfiguration {
	
	@Bean
	public ApiDispatcher httpApiDispatcher() {
		DefaultHttpApiDispatcher dispatcher =  new DefaultHttpApiDispatcher();
		SpringContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(dispatcher);
		return dispatcher;
	}
	
	@Bean
	public DefaultTransactionErrorHandler defaultTransactionErrorHandler() {
		return new DefaultTransactionErrorHandler();
	}
	
	@Bean
	public DefaultHttpAsyncTransactionHandler defaultHttpAsyncTransactionHandler() {
		DefaultHttpAsyncTransactionHandler handler = new DefaultHttpAsyncTransactionHandler();
		SpringContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(handler);
		return handler;
	}
	
	@Bean
	public DefaultHttpRealtimeTransactionHandler defaultHttpRealtimeTransactionHandler() {
		DefaultHttpRealtimeTransactionHandler handler = new DefaultHttpRealtimeTransactionHandler();
		SpringContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(handler);
		return handler;
	}
	
	@Bean
	public MessageRecordingPlugin messageRecordingPlugin() {
		MessageRecordingPlugin plugin = new MessageRecordingPlugin();
		SpringContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(plugin);
		return plugin;
	}
	
	@Bean
	public MessageValidateApiPlugin messageValidatePlugin() {
		MessageValidateApiPlugin plugin = new MessageValidateApiPlugin();
		SpringContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(plugin);
		return plugin;
	}
	
	@Bean(name="apiEventListeners")
	public List<ApiEventListener> apiEventListeners(){
		DefaultHttpApiEventListener defalutListener = new DefaultHttpApiEventListener();
		SpringContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(defalutListener);
		
		List<ApiEventListener> apiEventListeners = new ArrayList<ApiEventListener>(1);
		apiEventListeners.add(defalutListener);
		return apiEventListeners;
	}
	
	

}
