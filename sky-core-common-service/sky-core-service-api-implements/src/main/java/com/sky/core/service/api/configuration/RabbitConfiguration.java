package com.sky.core.service.api.configuration;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.sky.core.service.api.utils.RabbitUtil;

/**
 * rabbitmq通讯配置bean
 *
 */
@Configuration
public class RabbitConfiguration {
	@Value("${rabbit.hosts}")
	private String host;

	@Value("${rabbit.port}")
	private String port;

	@Value("${rabbit.username}")
	private String username;

	@Value("${rabbit.password}")
	private String password;

	@Value("${rabbit.publisher-confirms}")
	private Boolean publisherConfirms;

	@Value("${rabbit.vhost}")
	private String virtualHost;
	
	@Value("${rabbit.channelCacheSize}")
	private int channelCacheSize;
	
	private CacheMode cacheMode = CacheMode.CONNECTION;

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses(this.host);
		connectionFactory.setUsername(this.username);
		connectionFactory.setPassword(this.password);
		connectionFactory.setVirtualHost(this.virtualHost);
		connectionFactory.setPublisherConfirms(this.publisherConfirms);
		connectionFactory.setCacheMode(this.cacheMode);
		connectionFactory.setChannelCacheSize(this.channelCacheSize);
		return (ConnectionFactory)connectionFactory;
	}
    
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }
    
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }
    
    @Bean
    public RabbitUtil rabbitUtil(RabbitAdmin rabbitAdmin, RabbitTemplate rabbitTemplate) {
    	RabbitUtil rabbitUtil = new RabbitUtil(rabbitAdmin, rabbitTemplate);
    	return rabbitUtil;
    	
    }
    
    public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getPublisherConfirms() {
		return publisherConfirms;
	}

	public void setPublisherConfirms(Boolean publisherConfirms) {
		this.publisherConfirms = publisherConfirms;
	}

	public String getVirtualHost() {
		return virtualHost;
	}

	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}

	public static void main(String[] args) {
    	RabbitConfiguration cfg = new RabbitConfiguration();
	}
}