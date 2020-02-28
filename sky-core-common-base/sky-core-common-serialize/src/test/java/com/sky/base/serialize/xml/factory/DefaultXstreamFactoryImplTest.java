package com.sky.base.serialize.xml.factory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sky.base.serialize.xml.factory.DefaultXstreamFactoryImpl;
import com.sky.base.serialize.xml.factory.XstreamFactory;
import com.sky.base.serialize.xml.strategy.DefaultXstreamStrategy;
import com.sky.base.serialize.xml.strategy.XstreamStrategy;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-12
 */
public class DefaultXstreamFactoryImplTest {

    private static XstreamFactory xStreamFactory;

    public static XstreamFactory getDefaultXStreamFactory() {
        return new DefaultXstreamFactoryImpl();
    }

    @Before
    public void setUp() {
        xStreamFactory = getDefaultXStreamFactory();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(xStreamFactory.getInstance(null));
        XstreamStrategy strategy = new DefaultXstreamStrategy();
        assertNotNull(xStreamFactory.getInstance(strategy));
        strategy.setHierarchicalStreamDriver(null);
        assertNotNull(xStreamFactory.getInstance(strategy));
    }

}
