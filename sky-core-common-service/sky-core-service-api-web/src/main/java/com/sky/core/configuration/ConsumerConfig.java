package com.sky.core.configuration;

import com.sky.base.lang.logging.LoggingFormatter;
import com.sky.core.serialize.ApiSerializer;
import com.sky.core.serialize.XmlBasedApiSerializer;
import com.sky.core.utils.RabbitUtil;
import com.sky.service.api.struct.domain.SkyMsg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ConsumerConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerConfig.class);

    @Value("${rabbit.hosts}")
    private String host;

    @Value("${rabbit.port}")
    private int port;

    @Value("${rabbit.username}")
    private String username;

    @Value("${rabbit.password}")
    private String password;

    @Value("${rabbit.publisher-confirms}")
    private Boolean publisherConfirms;

    @Value("${rabbit.vhost}")
    private String virtualHost;

    @Value("${web.mq.listener.listenerConcurrentConsumers}")
    private int conCurrentConsumers;

    @Value("${web.mq.listener.maxListenerConcurrentConsumers}")
    private int maxConCurrentConsumers;

    @Value("${web.mq.listener.receiveTimeout}")
    private Long receiveTimeout;

    @Value("${web.mq.listener.preFetchCount}")
    private Integer preFetchCount;

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setPort(port); 
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);
        return factory;
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

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        //SimpleRabbitListenerContainerFactory发现消息中有content_type有text就会默认将其转换成string类型的
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(conCurrentConsumers);
        factory.setMaxConcurrentConsumers(maxConCurrentConsumers);
        factory.setReceiveTimeout(receiveTimeout);
        factory.setPrefetchCount(preFetchCount);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        LOGGER.info(LoggingFormatter.stdPrettyInfo("WEB-MQ-Api Listener Container Environment", 76));
        LOGGER.info(LoggingFormatter.subPrettyInfo(String.format("listener.concurrent.consumer.size -> [%s]", maxConCurrentConsumers), 76));
        LOGGER.info(LoggingFormatter.stdPrettyInfo("WEB-MQ-Api Listener Container Environment", 76));

        return factory;
    }
    
    @Bean
	public ApiSerializer apiSerializer() throws Exception{
		XmlBasedApiSerializer apiSerializer = new XmlBasedApiSerializer();
		apiSerializer.afterPropertiesSet();
		return apiSerializer;
	}

}
