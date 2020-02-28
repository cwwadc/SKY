package com.sky.service.api.support;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.sky.base.serialize.json.JsonUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { OrderedPluginChainTest.class })
@PropertySource(value = {"classpath:spring-context-test.properties"},  ignoreResourceNotFound=false)
@ComponentScan(basePackages="com.sky")
@Configuration
public class ApiContextSupportTest {
	private static final Logger logger = LoggerFactory.getLogger(ApiContextSupportTest.class);

	@Autowired
	private ApiContextSupport contextSupport;
    
    @Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Test
	public void beanInitializeTest() {
		List<ApiPlugin> requestPlugins = contextSupport.getStandardPlugins();
		Assert.notNull(requestPlugins);
		
		printObject(requestPlugins);
		
		List<ApiPlugin> errorPlugins = contextSupport.getErrorPlugins();
		Assert.notNull(errorPlugins);
		
		printObject(errorPlugins);
		
		Assert.isTrue(contextSupport.handlerStore.values().size() == 3);
		
	}
	
	public void printObject(Object o) {
		logger.info("--> {}", JsonUtils.toJsonString(o));
	}
	
	
}
