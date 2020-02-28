package com.sky.standalone.container.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.sky.standalone.container.component.AbstractLifecycleComponent;
import com.sky.standalone.container.component.LifecycleComponent;


public abstract class AbstractService<T extends LifecycleComponent> extends AbstractLifecycleComponent<T> implements ApplicationContextAware, LifecycleComponent<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);

    protected ApplicationContext applicationContext;

    protected String name() {
        return getClass().getName();
    }

    @Override 
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override 
    protected void doStart() throws Exception {
        LOGGER.info("Initializing service {}", name());
    }

    @Override 
    protected void doStop() throws Exception {
        LOGGER.info("Destroying service {}", name());
    }
}
