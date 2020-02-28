package com.sky.base.serialize.xml.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.base.serialize.xml.DomUtils;
import com.sky.base.serialize.xml.driver.DefaultXppDriver;
import com.sky.base.serialize.xml.strategy.DefaultXstreamStrategy;
import com.sky.base.serialize.xml.strategy.XstreamStrategy;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;

/**
 * @Title 默认XStream实例工厂实现
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-11
 */
public class DefaultXstreamFactoryImpl implements XstreamFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultXstreamFactoryImpl.class);
    
	static {
		try {
			Class.forName("com.thoughtworks.xstream.XStream", true, Thread.currentThread().getContextClassLoader());
		} catch (Exception e) {
			LOGGER.error("DefaultXstreamFactoryImpl initialize error", e);
		}
	}
    
//    private static XStream xStream;
//    static {
//		XstreamStrategy strategy = new DefaultXstreamStrategy();
//		HierarchicalStreamDriver driver = strategy.getHierarchicalStreamDriver();
//		if (driver == null) {
//			xStream = new XStream(new DefaultXppDriver());
//		} else {
//			xStream = new XStream(driver);
//		}
//		strategy.configure(xStream);
//    }

    @Override
    public XStream getInstance(XstreamStrategy strategy) {
        if (strategy == null) {
            LOGGER.warn("XStreamStrategy is null, use DefaultXStreamStrategy");
            strategy = new DefaultXstreamStrategy();
        }
        HierarchicalStreamDriver driver = strategy.getHierarchicalStreamDriver();
        XStream xStream = null;
        if (driver == null) {
            xStream = new XStream(new DefaultXppDriver());
        } else {
            xStream = new XStream(driver);
        }
        strategy.configure(xStream);
        return xStream;
    }

}
