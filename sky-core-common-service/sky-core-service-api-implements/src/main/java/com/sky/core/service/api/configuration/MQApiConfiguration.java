package com.sky.core.service.api.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.sky.base.context.spring.SpringContext;
import com.sky.core.service.api.event.mq.DefaultMQApiEventListener;
import com.sky.core.service.api.transaction.dispatcher.DefaultMQApiDispatcher;
import com.sky.core.service.api.transaction.handler.DefaultMQNotifyTransactionHandler;
import com.sky.core.service.api.transaction.handler.DefaultMQRealtimeTransactionHandler;
import com.sky.core.service.api.transaction.handler.DefaultMQReplyTransactionHandler;
import com.sky.core.service.api.transaction.handler.DefaultTransactionErrorHandler;
import com.sky.core.service.api.transaction.plugin.MessageRecordingPlugin;
import com.sky.core.service.api.transaction.plugin.MessageValidateApiPlugin;
import com.sky.service.api.event.ApiEventListener;
import com.sky.service.api.support.ApiDispatcher;

@Configuration
@Import({
	ApiConfiguration.class,
})
public class MQApiConfiguration {

	@Bean
	public ApiDispatcher mqApiDispatcher() {
		DefaultMQApiDispatcher dispatcher = new DefaultMQApiDispatcher();
		SpringContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(dispatcher);
		return dispatcher;
	}
	
	@Bean
	public DefaultTransactionErrorHandler defaultTransactionErrorHandler() {
		return new DefaultTransactionErrorHandler();
	}
	
	@Bean
	public DefaultMQNotifyTransactionHandler defaultMQNotifyTransactionHandler() {
		DefaultMQNotifyTransactionHandler handler = new DefaultMQNotifyTransactionHandler();
		SpringContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(handler);
		return handler;
	}
	
	@Bean
	public DefaultMQRealtimeTransactionHandler defaultMQRealtimeTransactionHandler() {
		DefaultMQRealtimeTransactionHandler handler = new DefaultMQRealtimeTransactionHandler();
		SpringContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(handler);
		return handler;
	}
	
	@Bean
	public DefaultMQReplyTransactionHandler defaultMQReplyTransactionHandler() {
		DefaultMQReplyTransactionHandler handler = new DefaultMQReplyTransactionHandler();
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
		DefaultMQApiEventListener defalutListener = new DefaultMQApiEventListener();
		SpringContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(defalutListener);
		
		List<ApiEventListener> apiEventListeners = new ArrayList<ApiEventListener>(1);
		apiEventListeners.add(defalutListener);
		return apiEventListeners;
	}

}
