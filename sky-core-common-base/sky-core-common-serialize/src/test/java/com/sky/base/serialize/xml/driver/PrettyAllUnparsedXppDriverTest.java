package com.sky.base.serialize.xml.driver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sky.base.serialize.xml.MockAnnotationsObject;
import com.sky.base.serialize.xml.driver.PrettyAllUnparsedXppDriver;
import com.sky.base.serialize.xml.driver.ConfigurableXppDriver.UnparsedCharacterDataTypeEnum;
import com.thoughtworks.xstream.XStream;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-12
 */
public class PrettyAllUnparsedXppDriverTest extends AbstractXppDriverTest {

    @Before
    public void setUp() {
        driver = new PrettyAllUnparsedXppDriver();
    }

    @Test
    public void testXmlFormatConfig() {
        assertFalse(driver.isCompactFormat());
        assertEquals(UnparsedCharacterDataTypeEnum.ALL_CDATA, driver.getCharacterDataType());
    }

    @Test
    public void testXmlFormat() {
        XStream xStream = getXStream(driver);

        MockAnnotationsObject object = new MockAnnotationsObject();

        object.setName("test");
        //		<xml>
        //		  <myname><![CDATA[test]]></myname>
        //		</xml>
        assertXmlFormat(xStream, object, simplePrettyFormat("<xml>%s<myname><![CDATA[test]]></myname>%s</xml>"));

        object.setName("test<");
        //		<xml>
        //		  <myname><![CDATA[test<]]></myname>
        //		</xml>
        assertXmlFormat(xStream, object, simplePrettyFormat("<xml>%s<myname><![CDATA[test<]]></myname>%s</xml>"));

        object.setName("<![CDATA[test]]>");
        //		<xml>
        //		  <myname><![CDATA[<![CDATA[test]]>]]></myname>
        //		</xml>
        assertXmlFormat(xStream, object,
                simplePrettyFormat("<xml>%s<myname><![CDATA[<![CDATA[test]]>]]></myname>%s</xml>"));

        object.setName("<![CDATA[test&]]>");
        //		<xml>
        //		  <myname><![CDATA[<![CDATA[test&]]>]]></myname>
        //		</xml>
        assertXmlFormat(xStream, object,
                simplePrettyFormat("<xml>%s<myname><![CDATA[<![CDATA[test&]]>]]></myname>%s</xml>"));
    }

}
