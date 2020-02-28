package com.sky.base.serialize.xml.strategy;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;

/**
 * @Title XStream实例初始化策略
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-11
 */
public class XstreamStrategy {

    private HierarchicalStreamDriver hierarchicalStreamDriver;

    private boolean autodetectAnnotations;

    private Integer mode;

    private boolean ignoreUnknownElements;

    private Class<?>[] processAnnotationsClasses;
    
    private Class<?>[] allowAnnotationClasses;

    public XstreamStrategy() {
        super();
    }

    public XstreamStrategy(HierarchicalStreamDriver hierarchicalStreamDriver) {
        this();
        this.hierarchicalStreamDriver = hierarchicalStreamDriver;
    }

    public void configure(XStream xStream) {
    	XStream.setupDefaultSecurity(xStream);
        if (autodetectAnnotations) {
            xStream.autodetectAnnotations(true);
        }
        if (mode != null) {
            xStream.setMode(mode);
        }
        if (ignoreUnknownElements) {
            xStream.ignoreUnknownElements();
        }
        if (processAnnotationsClasses != null && processAnnotationsClasses.length > 0) {
            xStream.processAnnotations(processAnnotationsClasses);
        }
        if (allowAnnotationClasses != null && allowAnnotationClasses.length > 0) {
        	for (Class<?> allowAnnotationClass : allowAnnotationClasses) {
        		xStream.allowTypeHierarchy(allowAnnotationClass);
			}
        } else {
        	xStream.allowTypeHierarchy(Object.class);
        }
        
    }

    public HierarchicalStreamDriver getHierarchicalStreamDriver() {
        return hierarchicalStreamDriver;
    }

    public void setHierarchicalStreamDriver(HierarchicalStreamDriver hierarchicalStreamDriver) {
        this.hierarchicalStreamDriver = hierarchicalStreamDriver;
    }

    public boolean isAutodetectAnnotations() {
        return autodetectAnnotations;
    }

    public void setAutodetectAnnotations(boolean autodetectAnnotations) {
        this.autodetectAnnotations = autodetectAnnotations;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public boolean isIgnoreUnknownElements() {
        return ignoreUnknownElements;
    }

    public void setIgnoreUnknownElements(boolean ignoreUnknownElements) {
        this.ignoreUnknownElements = ignoreUnknownElements;
    }

    public Class<?>[] getProcessAnnotationsClasses() {
        return processAnnotationsClasses;
    }

    public void setProcessAnnotationsClasses(Class<?>[] processAnnotationsClasses) {
        this.processAnnotationsClasses = processAnnotationsClasses;
    }

	public Class<?>[] getAllowAnnotationClasses() {
		return allowAnnotationClasses;
	}

	public void setAllowAnnotationClasses(Class<?>[] allowAnnotationClasses) {
		this.allowAnnotationClasses = allowAnnotationClasses;
	}
    
    

}
