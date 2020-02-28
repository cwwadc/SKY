package com.sky.standalone.container.lifecycle;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.sky.standalone.container.node.Node;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public abstract class AbstractContainer extends AbstractService<Container> implements Container {

    private final static String GATEWAY_CONFIGURATION_PROPERTY = "gateway.conf";
    private final static String GATEWAY_CONFIGURATION_ENV = "gateway.conf.env";

    protected boolean stopped = false;

    public AbstractContainer() {
        initialize();
    }

    protected void initialize() {
		try {
			initializeEnvironment();
			initializeLogging();
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error("An error occurs while initializing system", e);
		}

    }

    protected void initializeEnvironment() throws Exception {
        String gatewayConfiguration = System.getProperty(GATEWAY_CONFIGURATION_PROPERTY);
        if(StringUtils.isEmpty(gatewayConfiguration)) {
        	//未指定配置文件时启动策略
        	LoggerFactory.getLogger(this.getClass()).debug("did not specified a system configuration file, use default configuration search policy.");
        	String profileEnv = System.getProperty(GATEWAY_CONFIGURATION_ENV);
        	LoggerFactory.getLogger(this.getClass()).info("profile active -> {}", StringUtils.isEmpty(profileEnv) ? "default" : profileEnv);
        	
        	String defaultConfigurationName = StringUtils.isEmpty(profileEnv) ? "application.properties" : String.format("application-%s.properties", profileEnv);
        	Resource defaultConfigurationResource = new ClassPathResource("./" + defaultConfigurationName);
        	if(!defaultConfigurationResource.exists()) {
        		throw new RuntimeException("configuration resource not found in classpath -> " + defaultConfigurationName);
        	}
        	LoggerFactory.getLogger(this.getClass()).debug("gateway.conf -> {}", defaultConfigurationResource.getFile().getAbsolutePath());
        	System.setProperty(GATEWAY_CONFIGURATION_PROPERTY, defaultConfigurationResource.getFile().getAbsolutePath());
        }
    }

    protected void initializeLogging() throws Exception {
    	Resource loggingConfigResource = new ClassPathResource("./logback.xml");
    	if(!loggingConfigResource.exists()) {
    		throw new RuntimeException("cannot found logging configuration");
    	}
        File logbackConfigurationfile = new File(loggingConfigResource.getFile().getAbsolutePath());
        if (logbackConfigurationfile.exists()) {
            System.setProperty("logback.configurationFile", logbackConfigurationfile.getAbsolutePath());
            StaticLoggerBinder loggerBinder = StaticLoggerBinder.getSingleton();
            LoggerContext loggerContext = (LoggerContext) loggerBinder.getLoggerFactory();
            
            loggerContext.reset();
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(loggerContext);
            try {
                configurator.doConfigure(logbackConfigurationfile);
            } catch( JoranException e ) {
                LoggerFactory.getLogger(this.getClass()).error("An error occurs while initializing logging system", e);
            }

            StatusPrinter.printInCaseOfErrorsOrWarnings(loggerContext);
        }
    }

    @Override
    protected void doStart() throws Exception {
        LoggerFactory.getLogger(AbstractContainer.class).info("Starting {}...", name());

        try {
            final Node node = node();
            node.start();

            Thread shutdownHook = new ContainerShutdownHook(node);
            shutdownHook.setName("gateway-finalizer");
            Runtime.getRuntime().addShutdownHook(shutdownHook);
        } catch (Exception ex) {
            LoggerFactory.getLogger(this.getClass()).error("An unexpected error occurs while starting {}", name(), ex);
            stop();
        }
    }

    @Override
    protected void doStop() throws Exception {
        if (! stopped) {
            LoggerFactory.getLogger(this.getClass()).info("Shutting-down {}...", name());

            try {
                node().stop();
            } catch (Exception ex) {
                LoggerFactory.getLogger(this.getClass()).error("Unexpected error", ex);
            } finally {
                stopped = true;
            }
        }
    }

    private class ContainerShutdownHook extends Thread {

        private final Node node;

        private ContainerShutdownHook(Node node) {
            this.node = node;
        }

        @Override
        public void run() {
            if (node != null) {
                try {
                    AbstractContainer.this.stop();
                } catch (Exception ex) {
                    LoggerFactory.getLogger(this.getClass()).error("Unexpected error while stopping {}", name(), ex);
                }
            }
        }
    }
   
}
