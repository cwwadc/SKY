package com.sky.base.serialize.xml.strategy;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.sky.base.serialize.AbstractSerializeTest;
import com.sky.base.serialize.xml.MockAnnotationsObject;
import com.sky.base.serialize.xml.driver.DefaultXppDriver;
import com.sky.base.serialize.xml.strategy.XstreamStrategy;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter.UnknownFieldException;
import com.sky.base.lang.string.StringUtils;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-12
 */
@Ignore
public class XstreamStrategyTest extends AbstractSerializeTest {

    @Test
    public void testConfigureAutodetectAnnotations() throws InstantiationException {
        String xml = "<com.ursa.base.serialize.xml.MockAnnotationsObject><name>test</name><age>3</age></com.ursa.base.serialize.xml.MockAnnotationsObject>";
        MockAnnotationsObject object = new MockAnnotationsObject();
        object.setName("test");

        XStream xStream = new XStream(new DefaultXppDriver());
        XstreamStrategy strategy = new XstreamStrategy();
        strategy.setAutodetectAnnotations(true);
        strategy.setIgnoreUnknownElements(true);
        strategy.setMode(XStream.NO_REFERENCES);

        // configure before
        assertTrue(StringUtils.contains(xStream.toXML(object), "<name>test</name>"));
        assertFalse(StringUtils.contains(xStream.toXML(object), "<myname>test</myname>"));
        try {
            xStream.fromXML(xml);
            fail("Expected Exception");
        } catch (UnknownFieldException e) {
            assertTrue(StringUtils.contains(e.getMessage(),
                    "No such field com.ursa.base.serialize.xml.MockAnnotationsObject.age"));
        }

        strategy.configure(xStream);

        // configure after
        assertFalse(StringUtils.contains(xStream.toXML(object), "<name>test</name>"));
        assertTrue(StringUtils.contains(xStream.toXML(object), "<myname>test</myname>"));
        xStream.fromXML(xml);

    }

    @Test
    public void testConfigureProcessAnnotationsClasses() throws InstantiationException {
        String xml = "<com.ursa.base.serialize.xml.MockAnnotationsObject><name>test</name><age>3</age></com.ursa.base.serialize.xml.MockAnnotationsObject>";
        MockAnnotationsObject object = new MockAnnotationsObject();
        object.setName("test");

        XStream xStream = new XStream(new DefaultXppDriver());
        XstreamStrategy strategy = new XstreamStrategy();
        strategy.setIgnoreUnknownElements(true);
        strategy.setMode(XStream.NO_REFERENCES);
        strategy.setProcessAnnotationsClasses(new Class<?>[] { MockAnnotationsObject.class });

        // configure before
        assertTrue(StringUtils.contains(xStream.toXML(object), "<name>test</name>"));
        assertFalse(StringUtils.contains(xStream.toXML(object), "<myname>test</myname>"));
        try {
            xStream.fromXML(xml);
            fail("Expected Exception");
        } catch (UnknownFieldException e) {
            assertTrue(StringUtils.contains(e.getMessage(),
                    "No such field com.ursa.base.serialize.xml.MockAnnotationsObject.age"));
        }

        strategy.configure(xStream);

        // configure after
        assertFalse(StringUtils.contains(xStream.toXML(object), "<name>test</name>"));
        assertTrue(StringUtils.contains(xStream.toXML(object), "<myname>test</myname>"));
        xStream.fromXML(xml);

    }

    @Test
    public void testStrategyConfig() {
        XstreamStrategy strategy = new XstreamStrategy();
        strategy.setAutodetectAnnotations(true);
        strategy.setIgnoreUnknownElements(true);
        strategy.setMode(XStream.NO_REFERENCES);
        strategy.setProcessAnnotationsClasses(new Class<?>[] { MockAnnotationsObject.class });
        assertTrue(strategy.isAutodetectAnnotations());
        assertTrue(strategy.isIgnoreUnknownElements());
        assertEquals(new Integer(XStream.NO_REFERENCES), strategy.getMode());
        assertThat(strategy.getProcessAnnotationsClasses().length, is(1));
    }

}
