package com.sky.standalone.http.connector.container;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.standalone.container.lifecycle.SpringBasedContainer;
import com.sky.standalone.http.connector.configuration.HttpConnectorConfiguration;

public class HttpApiContainer extends SpringBasedContainer {
	private static final Logger logger = LoggerFactory.getLogger(HttpApiContainer.class);

	@Override
	protected List<Class<?>> annotatedClasses() {
		List<Class<?>> classes = super.annotatedClasses();
		classes.add(HttpConnectorConfiguration.class);
		return classes;
	}

	@Override
	protected String name() {
		return "SKY.io - HTTP API Container";
	}
}
