package com.sky.standalone.container.env;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import java.util.Properties;


@Configuration
@Import({
        PropertiesConfiguration.class
})
public class EnvironmentConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties(@Qualifier("gatewayProperties") Properties gatewayProperties) {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setProperties(gatewayProperties);
        propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);

        return propertySourcesPlaceholderConfigurer;
    }

    @Bean
    public static PropertySourceBeanProcessor propertySourceBeanProcessor(@Qualifier("gatewayProperties") Properties gatewayProperties,
                                                                          Environment environment) {

        return new PropertySourceBeanProcessor(gatewayProperties, environment);
    }

    @Bean
    public static EnvironmentBeanFactoryPostProcessor environmentBeanFactoryPostProcessor() {
        return new EnvironmentBeanFactoryPostProcessor();
    }
}
