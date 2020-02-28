package com.sky.standalone.container.lifecycle;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sky.standalone.container.env.EnvironmentConfiguration;
import com.sky.standalone.container.env.PropertiesConfiguration;
import com.sky.standalone.container.node.Node;

import java.util.ArrayList;
import java.util.List;

public abstract class SpringBasedContainer extends AbstractContainer {

    private ConfigurableApplicationContext ctx;

    public SpringBasedContainer() {
        super();
    }

    @Override
    protected void initialize() {
        super.initialize();

        this.initializeContext();
    }

    protected void initializeContext() {
        ctx = new AnnotationConfigApplicationContext();

        List<Class<?>> classes = annotatedClasses();
        classes.forEach(aClass -> ((AnnotationConfigApplicationContext)ctx).register(aClass));

        ctx.refresh();
    }

    protected List<Class<?>> annotatedClasses() {
        List<Class<?>> classes = new ArrayList<>();

        classes.add(EnvironmentConfiguration.class);
        classes.add(PropertiesConfiguration.class);

        return classes;
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
                ctx.close();
                stopped = true;
            }
        }
    }

    @Override
    public Node node() {
        return ctx.getBean(Node.class);
    }

    public ApplicationContext applicationContext() {
        return ctx;
    }
}
