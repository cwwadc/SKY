package com.sky.standalone.container.env;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;


public class PropertySourceBeanProcessor implements BeanFactoryPostProcessor, Ordered {

    private Environment environment;

    private Properties properties;

    PropertySourceBeanProcessor(Properties properties, Environment environment) {
        this.properties = properties;
        this.environment = environment;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        ((ConfigurableEnvironment) environment).getPropertySources().addLast(
                new PropertiesPropertySource("gatewayConfiguration", properties));
    }
}

