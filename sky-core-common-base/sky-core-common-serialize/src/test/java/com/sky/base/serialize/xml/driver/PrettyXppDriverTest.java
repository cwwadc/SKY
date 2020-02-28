package com.sky.base.serialize.xml.driver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sky.base.serialize.xml.MockAnnotationsObject;
import com.sky.base.serialize.xml.driver.PrettyXppDriver;
import com.sky.base.serialize.xml.driver.ConfigurableXppDriver.UnparsedCharacterDataTypeEnum;
import com.thoughtworks.xstream.XStream;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-12
 */
public class PrettyXppDriverTest extends AbstractXppDriverTest {

    @Before
    public void setUp() {
        driver = new PrettyXppDriver();
    }

    @Test
    public void testXmlFormatConfig() {
        assertFalse(driver.isCompactFormat());
        assertEquals(UnparsedCharacterDataTypeEnum.NO_CDATA, driver.getCharacterDataType());
    }

    @Test
    public void testXmlFormat() {
        XStream xStream = getXStream(driver);

        MockAnnotationsObject object = new MockAnnotationsObject();

        object.setName("test");
        //		<xml>
        //		  <myname>test</myname>
        //		</xml>
        assertXmlFormat(xStream, object, simplePrettyFormat("<xml>%s<myname>test</myname>%s</xml>"));

        object.setName("test<");
        //		<xml>
        //		  <myname>test&lt;</myname>
        //		</xml>
        assertXmlFormat(xStream, object, simplePrettyFormat("<xml>%s<myname>test&lt;</myname>%s</xml>"));

        object.setName("<![CDATA[test]]>");
        //		<xml>
        //		  <myname>&lt;![CDATA[test]]&gt;</myname>
        //		</xml>
        assertXmlFormat(xStream, object, simplePrettyFormat("<xml>%s<myname>&lt;![CDATA[test]]&gt;</myname>%s</xml>"));

        object.setName("<![CDATA[test&]]>");
        //		<xml>
        //		  <myname>&lt;![CDATA[test&amp;]]&gt;</myname>
        //		</xml>
        assertXmlFormat(xStream, object,
                simplePrettyFormat("<xml>%s<myname>&lt;![CDATA[test&amp;]]&gt;</myname>%s</xml>"));
    }
}
