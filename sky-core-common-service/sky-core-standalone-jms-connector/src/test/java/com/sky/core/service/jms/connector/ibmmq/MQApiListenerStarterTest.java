package com.sky.core.service.jms.connector.ibmmq;

import java.util.ArrayList;
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

import com.sky.base.serialize.json.JsonUtils;
import com.sky.service.api.struct.pojo.SkyMQChnlCfg;
import com.sky.standalone.jms.connector.webspheremq.MQApiListenerContainer;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MQApiListenerStarterTest.class })
@PropertySource(value = {"classpath:application-dev.properties"},  ignoreResourceNotFound=false)
@ComponentScan(basePackages="com.sky")
@Configuration
public class MQApiListenerStarterTest {
	private static final Logger logger = LoggerFactory.getLogger(MQApiListenerStarterTest.class);
    
    @Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
    
    @Autowired
    private MQApiListenerContainer starter;
	
	@Test
	public void beanInitializeTest() {
		try {
			starter = new MQApiListenerContainer();
			List<SkyMQChnlCfg> cfgs = new ArrayList<SkyMQChnlCfg>();
			SkyMQChnlCfg cfg = new SkyMQChnlCfg();
			cfgs.add(cfg);
			cfg.setqType("IBMMQ");
			cfg.setBindingUri("ibmmq-admin:111111@127.0.0.1:1414:QMGR_DEF:1381:CHNL.RECV:Q.RECV");
			cfg.setConfigName("test-group");
			cfg.setReceiveTimeout(300000L);
			cfg.setRecoveryInterval(3000L);
			cfg.setThreadNum(100);
			starter.setChnls(cfgs);
			
			starter.start();
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void printObject(Object o) {
		logger.info("--> {}", JsonUtils.toJsonString(o));
	}
	
	
}
