package com.sky.standalone.container.env;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
public class PropertiesConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesConfiguration.class);

    private final static String GATEWAY_CONFIGURATION_PROPERTY = "gateway.conf";

    @Bean(name = "gatewayProperties")
    public static Properties gatewayProperties() throws IOException {
        LOGGER.info("Loading Gateway configuration.");

        PropertiesFactoryBean confFactory = new PropertiesFactoryBean();

        String gatewayConfiguration = System.getProperty(GATEWAY_CONFIGURATION_PROPERTY);
        Resource resource = new FileSystemResource(gatewayConfiguration);

        LOGGER.info("\tGateway configuration loaded from {}", resource.getURL().getPath());
        
        confFactory.setLocation(resource);
        confFactory.afterPropertiesSet();
        Properties properties = confFactory.getObject();
        LOGGER.info("Loading Gateway configuration. DONE");

        return properties;
    }
}
