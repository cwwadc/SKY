package com.sky.test.mock.server.fax.bootstrap;


import javax.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.base.lang.logging.LoggingFormatter;
import com.sky.test.mock.server.fax.service.ws.MockWsInterfaceImpl;

public class MockWsServerBootstrap {
	private static final Logger logger = LoggerFactory.getLogger(MockWsServerBootstrap.class);
	private static final String DEFAULT_BINDING_ADDR = "http://127.0.0.1:7011/ws/wbserver";
	
	public static void main(String[] args) {
		try {
			String bindingAddress = System.getProperty("application.ws.binding.address", DEFAULT_BINDING_ADDR);
			Endpoint.publish(bindingAddress , new MockWsInterfaceImpl());
			
			logger.info(LoggingFormatter.stdPrettyInfo("Mock WS-SERVER Environment", 76));
			logger.info(LoggingFormatter.subPrettyInfo(String.format("starting at binding -> [%s]", bindingAddress), 76));
			logger.info(LoggingFormatter.stdPrettyInfo("Mock WS-SERVER Environment", 76));
		} catch (Exception e) {
			logger.error("Mock WS-SERVER start error", e);
		}
		
	}
}
