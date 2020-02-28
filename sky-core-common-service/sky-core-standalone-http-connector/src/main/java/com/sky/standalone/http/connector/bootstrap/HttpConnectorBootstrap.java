package com.sky.standalone.http.connector.bootstrap;

import com.sky.standalone.container.bootstrap.Bootstrap;

public class HttpConnectorBootstrap extends Bootstrap {
	private static final String CONTAINER_CLASS = "com.sky.standalone.http.connector.container.HttpApiContainer";
	private static final String CONTAINER_MAIN = "http-api-node";
	
	public static void main(String[] args) {
		try {
			HttpConnectorBootstrap bootstrap = new HttpConnectorBootstrap();
			bootstrap.settingUp(CONTAINER_CLASS, CONTAINER_MAIN);
			bootstrap.start();
		} catch (Exception e) {
			e.printStackTrace();
            System.exit(1);
		}
	}
}
