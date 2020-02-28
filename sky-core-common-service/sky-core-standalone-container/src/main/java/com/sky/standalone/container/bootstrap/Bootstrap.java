package com.sky.standalone.container.bootstrap;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

public abstract class Bootstrap {
	
    protected static String containerClass;
    protected Object containerDaemon = null;
    
    protected void settingUp(String containerClass, String containerMain) {
    	Bootstrap.containerClass = containerClass;
    	Thread mainThread = Thread.currentThread();
        mainThread.setName(containerMain);
    }

    public void init() throws Exception {
    	if(StringUtils.isEmpty(containerClass)) {
    		throw new RuntimeException("An error occurs while initializing system, container-class do not specified.");
    	}
    	Class<?> fwClass = Thread.currentThread().getContextClassLoader().loadClass(containerClass);
        containerDaemon = fwClass.newInstance();
    }

    public void start() throws Exception {
        if (containerDaemon == null) {
            init();
        }

        Method method = containerDaemon.getClass().getMethod("start", (Class[]) null);
        method.invoke(containerDaemon, (Object[]) null);
    }

}
