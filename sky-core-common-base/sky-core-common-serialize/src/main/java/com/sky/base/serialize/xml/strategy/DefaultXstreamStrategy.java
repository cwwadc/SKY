package com.sky.base.serialize.xml.strategy;

import com.sky.base.serialize.xml.driver.DefaultXppDriver;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;

/**
 * @Title 默认XML初始化策略
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-11
 */
public class DefaultXstreamStrategy extends XstreamStrategy {

    public DefaultXstreamStrategy() {
        super();
        setHierarchicalStreamDriver(new DefaultXppDriver());
        defaultConfiguration();
    }

    public DefaultXstreamStrategy(HierarchicalStreamDriver hierarchicalStreamDriver) {
        super(hierarchicalStreamDriver);
        defaultConfiguration();
    }

    protected void defaultConfiguration() {
        setAutodetectAnnotations(true);
        setIgnoreUnknownElements(true);
        setMode(XStream.NO_REFERENCES);
    }

}
