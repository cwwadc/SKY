package com.sky.core.service.api.mock.server.ws;


import javax.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockWsBootstrap {
	private static final Logger logger = LoggerFactory.getLogger(MockWsBootstrap.class);
	
	public static void main(String[] args) {
		String address = "http://127.0.0.1:7011/ws/wbserver";
		Endpoint.publish(address , new MockWsInterfaceImpl());
		logger.info("ws-server start, bind address -> {}", address);

	}
}
