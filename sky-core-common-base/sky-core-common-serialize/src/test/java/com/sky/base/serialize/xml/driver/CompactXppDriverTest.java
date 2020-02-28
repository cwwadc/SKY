package com.sky.base.serialize.xml.driver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sky.base.serialize.xml.MockAnnotationsObject;
import com.sky.base.serialize.xml.driver.CompactXppDriver;
import com.sky.base.serialize.xml.driver.ConfigurableXppDriver.UnparsedCharacterDataTypeEnum;
import com.thoughtworks.xstream.XStream;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-12
 */
public class CompactXppDriverTest extends AbstractXppDriverTest {

    @Before
    public void setUp() {
        driver = new CompactXppDriver();
    }

    @Test
    public void testXmlFormatConfig() {
        assertTrue(driver.isCompactFormat());
        assertEquals(UnparsedCharacterDataTypeEnum.NO_CDATA, driver.getCharacterDataType());
    }

    @Test
    public void testXmlFormat() {
        XStream xStream = getXStream(driver);

        MockAnnotationsObject object = new MockAnnotationsObject();

        object.setName("test");
        assertXmlFormat(xStream, object, "<xml><myname>test</myname></xml>");

        object.setName("test<");
        assertXmlFormat(xStream, object, "<xml><myname>test&lt;</myname></xml>");

        object.setName("<![CDATA[test]]>");
        assertXmlFormat(xStream, object, "<xml><myname>&lt;![CDATA[test]]&gt;</myname></xml>");

        object.setName("<![CDATA[test&]]>");
        assertXmlFormat(xStream, object, "<xml><myname>&lt;![CDATA[test&amp;]]&gt;</myname></xml>");
    }

}
