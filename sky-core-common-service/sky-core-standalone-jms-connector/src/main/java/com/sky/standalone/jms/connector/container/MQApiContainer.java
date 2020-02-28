package com.sky.standalone.jms.connector.container;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.standalone.container.lifecycle.SpringBasedContainer;
import com.sky.standalone.jms.connector.configuration.JmsConnectorConfiguration;

public class MQApiContainer extends SpringBasedContainer {
	private static final Logger logger = LoggerFactory.getLogger(MQApiContainer.class);
	
	 @Override
	    protected List<Class<?>> annotatedClasses() {
	        List<Class<?>> classes = super.annotatedClasses();
	        classes.add(JmsConnectorConfiguration.class);
	        return classes;
	    }

	    @Override
	    protected String name() {
	        return "Sky.io - MQ API Container";
	    }
}
