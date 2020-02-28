package com.sky.standalone.jms.connector.bootstrap;

import com.sky.standalone.container.bootstrap.Bootstrap;

public class JmsConnectorBootstrap extends Bootstrap {
	private static final String CONTAINER_CLASS = "com.sky.standalone.jms.connector.container.MQApiContainer";
	private static final String CONTAINER_MAIN = "mq-api-node";
	
	public static void main(String[] args) {
		try {
			JmsConnectorBootstrap bootstrap = new JmsConnectorBootstrap();
			bootstrap.settingUp(CONTAINER_CLASS, CONTAINER_MAIN);
			bootstrap.start();
		} catch (Exception e) {
			e.printStackTrace();
            System.exit(1);
		}
	}
}
