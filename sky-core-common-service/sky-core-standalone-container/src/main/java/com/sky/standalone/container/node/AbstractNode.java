package com.sky.standalone.container.node;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.sky.standalone.container.component.LifecycleComponent;
import com.sky.standalone.container.component.Lifecycle;
import com.sky.standalone.container.lifecycle.AbstractService;
import com.sky.standalone.container.utils.ListReverser;
import com.sky.standalone.container.utils.Version;

public abstract class AbstractNode extends AbstractService<Node> implements Node, ApplicationContextAware {

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    protected ApplicationContext applicationContext;

    private String hostname;

    public AbstractNode() {
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException uhe) {
            LOGGER.warn("Could not get hostname / IP", uhe);
        }
    }

    protected void doStart() throws Exception {
        this.LOGGER.info("{} is now starting...", this.name());
        long startTime = System.currentTimeMillis();
        List<Class<? extends LifecycleComponent>> components = this.components();
        Iterator<Class<? extends LifecycleComponent>> componentsIte = components.iterator();

        while(componentsIte.hasNext()) {
            Class<? extends LifecycleComponent> componentClass = componentsIte.next();
            this.LOGGER.info("\tStarting component: {}", componentClass.getSimpleName());

            try {
                LifecycleComponent lifecyclecomponent = this.applicationContext.getBean(componentClass);
                lifecyclecomponent.start();
            } catch (Exception var7) {
                this.LOGGER.error("An error occurs while starting component {}", componentClass.getSimpleName(), var7);
                throw var7;
            }
        }

        long endTime = System.currentTimeMillis();
        String processId = ManagementFactory.getRuntimeMXBean().getName();
        if(processId.contains("@")) {
            processId = processId.split("@")[0];
        }

        this.LOGGER.info(
                "{} id[{}] version[{}] pid[{}] build[{}#{}] jvm[{}/{}/{}] started in {} ms.",
                this.name(), this.id(),
                Version.RUNTIME_VERSION.MAJOR_VERSION, processId,
                Version.RUNTIME_VERSION.BUILD_NUMBER, Version.RUNTIME_VERSION.REVISION,
                ManagementFactory.getRuntimeMXBean().getVmVendor(), ManagementFactory.getRuntimeMXBean().getVmName(),
                ManagementFactory.getRuntimeMXBean().getVmVersion(), endTime - startTime);
    }

    protected void doStop() throws Exception {
        this.LOGGER.info("{} is stopping", this.name());
        ListReverser<Class<? extends LifecycleComponent>> components = new ListReverser(this.components());
        Iterator var2 = components.iterator();

        while(var2.hasNext()) {
            Class componentClass = (Class)var2.next();

            try {
                LifecycleComponent lifecyclecomponent = (LifecycleComponent)this.applicationContext.getBean(componentClass);
                if(lifecyclecomponent.lifecycleState() == Lifecycle.State.STARTED) {
                    this.LOGGER.info("\tStopping component: {}", componentClass.getSimpleName());
                    lifecyclecomponent.stop();
                }
            } catch (Exception var5) {
                this.LOGGER.error("An error occurs while stopping component {}", componentClass.getSimpleName(), var5);
            }
        }

        this.LOGGER.info("{} stopped", this.name());
    }

    @Override
    public String hostname() {
        return hostname;
    }

    public abstract String name();

    @Override
    public List<Class<? extends LifecycleComponent>> components() {
        List<Class<? extends LifecycleComponent>> components = new ArrayList<>();

        return components;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}