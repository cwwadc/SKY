package com.sky.core.service.api.callback;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sky.base.context.spring.SpringContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApiCallbackReatorTest.class })
@PropertySource(value = {"classpath:application-dev.properties"},  ignoreResourceNotFound=false)
@ComponentScan(basePackages= {"com.sky.core.service.api.callback", "com.sky.core.service.api.configuration"})
@Configuration
public class ApiCallbackReatorTest {
	private static final Logger logger = LoggerFactory.getLogger(ApiCallbackReatorTest.class);
    
    @Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
    
    @Test
    public void callbackTest() {
    	try {
    		RmiServiceExporter rmiServiceExporter = SpringContext.getBean("callbackRmiExporter");
        	System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
}