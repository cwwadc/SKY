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
import com.sky.service.api.support.chain.OrderedPluginChain;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { OrderedPluginChainTest.class })
@PropertySource(value = {"classpath:spring-context-test.properties"},  ignoreResourceNotFound=false)
@ComponentScan(basePackages="com.sky")
@Configuration
public class OrderedPluginChainTest {

	private static final Logger logger = LoggerFactory.getLogger(OrderedPluginChainTest.class);
	
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
		OrderedPluginChain requestChain = new OrderedPluginChain(requestPlugins, contextSupport.defaultApiHandler());
		
		
		List<ApiPlugin> errorPlugins = contextSupport.getErrorPlugins();
		Assert.notNull(errorPlugins);
		OrderedPluginChain errorChain = new OrderedPluginChain(errorPlugins, null);
		
		ApiContext context = new ApiContext();
		printListObject(requestPlugins);
		requestChain.activatePluginChain(context);
		printListObject(errorPlugins);
		errorChain.activatePluginChain(context);
		
	}
	
	public void printListObject(List<?> list) {
		logger.info("--> {}", JsonUtils.toJsonString(list));
	}
}
