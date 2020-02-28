package com.sky.standalone.container.env;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import com.sky.standalone.container.utils.RelaxedPropertySource;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private final static String[] PROPERTY_PREFIXES = new String[] {"gateway.", "gateway", "GATEWAY." , "GATEWAY_"};

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        StandardEnvironment environment = (StandardEnvironment) beanFactory.getBean(Environment.class);

        if (environment != null) {
            Map<String, Object> systemEnvironment = environment.getSystemEnvironment();
            Map<String, Object> prefixlessSystemEnvironment = new HashMap<>(systemEnvironment.size());
            systemEnvironment
                    .keySet()
                    .forEach(key -> {
                        String prefixKey = key;
                        for (String propertyPrefix : PROPERTY_PREFIXES) {
                            if (key.startsWith(propertyPrefix)) {
                                prefixKey = key.substring(propertyPrefix.length());
                                break;
                            }
                        }
                        prefixlessSystemEnvironment.put(prefixKey, systemEnvironment.get(key));
                    });

            environment.getPropertySources().replace(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME,
                    new RelaxedPropertySource(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, prefixlessSystemEnvironment));
        }
    }
}

