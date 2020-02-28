package com.sky.base.serialize.xml;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.sky.base.serialize.xml.XmlUtils;
import com.sky.base.serialize.xml.strategy.DefaultXstreamStrategy;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-12
 */
public class XmlUtilsTest extends AbstractXmlTest {

    @Test
    public void testToXmlT() {

        assertEquals("<null/>", XmlUtils.toXml(null));
        assertEquals("<string></string>", XmlUtils.toXml(""));

        MockAnnotationsObject object = new MockAnnotationsObject();
        object.setName("test");
        //		<xml>
        //		  <myname>test</myname>
        //		</xml>
        assertEquals(simplePrettyFormat("<xml>%s<myname>test</myname>%s</xml>"), XmlUtils.toXml(object));

        object.setName("test<");
        //		<xml>
        //		  <myname>test&lt;</myname>
        //		</xml>
        assertEquals(simplePrettyFormat("<xml>%s<myname>test&lt;</myname>%s</xml>"), XmlUtils.toXml(object));

        object.setName("<![CDATA[test]]>");
        //		<xml>
        //		  <myname>&lt;![CDATA[test]]&gt;</myname>
        //		</xml>
        assertEquals(simplePrettyFormat("<xml>%s<myname>&lt;![CDATA[test]]&gt;</myname>%s</xml>"),
                XmlUtils.toXml(object));

        object.setName("<![CDATA[test&]]>");
        //		<xml>
        //		  <myname>&lt;![CDATA[test&amp;]]&gt;</myname>
        //		</xml>
        assertEquals(simplePrettyFormat("<xml>%s<myname>&lt;![CDATA[test&amp;]]&gt;</myname>%s</xml>"),
                XmlUtils.toXml(object));
    }

    @Test
    public void testFromXmlStringClassOfT() {
        MockAnnotationsObject object = null;
        //		<xml><myname>test</myname></xml>
        object = XmlUtils.fromXml("<xml><myname>test</myname></xml>", MockAnnotationsObject.class);
        assertEquals("test", object.getName());

        //		<xml>
        //		  <myname>test&lt;</myname>
        //		</xml>
        object = XmlUtils.fromXml(simplePrettyFormat("<xml>%s<myname>test&lt;</myname>%s</xml>"),
                MockAnnotationsObject.class);
        assertEquals("test<", object.getName());

        //		<xml>
        //		  <myname>&lt;![CDATA[test]]&gt;</myname>
        //		</xml>
        object = XmlUtils.fromXml(simplePrettyFormat("<xml>%s<myname>&lt;![CDATA[test]]&gt;</myname>%s</xml>"),
                MockAnnotationsObject.class);
        assertEquals("<![CDATA[test]]>", object.getName());

        //		<xml><myname>&lt;![CDATA[test&amp;]]&gt;</myname></xml>
        object = XmlUtils.fromXml("<xml>%s<myname>&lt;![CDATA[test&amp;]]&gt;</myname>%s</xml>",
                MockAnnotationsObject.class);
        assertEquals("<![CDATA[test&]]>", object.getName());
    }

    @Test
    public void testToXmlTXStreamStrategy() {

        assertEquals("<null/>", XmlUtils.toXml(null, new DefaultXstreamStrategy()));
        assertEquals("<string></string>", XmlUtils.toXml("", new DefaultXstreamStrategy()));

        MockAnnotationsObject object = new MockAnnotationsObject();
        object.setName("test");
        //		<xml>
        //		  <myname>test</myname>
        //		</xml>
        assertEquals(simplePrettyFormat("<xml>%s<myname>test</myname>%s</xml>"),
                XmlUtils.toXml(object, new DefaultXstreamStrategy()));

        object.setName("test<");
        //		<xml>
        //		  <myname>test&lt;</myname>
        //		</xml>
        assertEquals(simplePrettyFormat("<xml>%s<myname>test&lt;</myname>%s</xml>"),
                XmlUtils.toXml(object, new DefaultXstreamStrategy()));

        object.setName("<![CDATA[test]]>");
        //		<xml>
        //		  <myname>&lt;![CDATA[test]]&gt;</myname>
        //		</xml>
        assertEquals(simplePrettyFormat("<xml>%s<myname>&lt;![CDATA[test]]&gt;</myname>%s</xml>"),
                XmlUtils.toXml(object, new DefaultXstreamStrategy()));

        object.setName("<![CDATA[test&]]>");
        //		<xml>
        //		  <myname>&lt;![CDATA[test&amp;]]&gt;</myname>
        //		</xml>
        assertEquals(simplePrettyFormat("<xml>%s<myname>&lt;![CDATA[test&amp;]]&gt;</myname>%s</xml>"),
                XmlUtils.toXml(object, new DefaultXstreamStrategy()));
    }

    @Test
    public void testFromXmlStringClassOfTXStreamStrategy() {
        MockAnnotationsObject object = null;
        //		<xml><myname>test</myname></xml>
        object = XmlUtils.fromXml("<xml><myname>test</myname></xml>", MockAnnotationsObject.class,
                new DefaultXstreamStrategy());
        assertEquals("test", object.getName());

        //		<xml>
        //		  <myname>test&lt;</myname>
        //		</xml>
        object = XmlUtils.fromXml(simplePrettyFormat("<xml>%s<myname>test&lt;</myname>%s</xml>"),
                MockAnnotationsObject.class, new DefaultXstreamStrategy());
        assertEquals("test<", object.getName());

        //		<xml>
        //		  <myname>&lt;![CDATA[test]]&gt;</myname>
        //		</xml>
        object = XmlUtils.fromXml(simplePrettyFormat("<xml>%s<myname>&lt;![CDATA[test]]&gt;</myname>%s</xml>"),
                MockAnnotationsObject.class, new DefaultXstreamStrategy());
        assertEquals("<![CDATA[test]]>", object.getName());

        //		<xml><myname>&lt;![CDATA[test&amp;]]&gt;</myname></xml>
        object = XmlUtils.fromXml("<xml>%s<myname>&lt;![CDATA[test&amp;]]&gt;</myname>%s</xml>",
                MockAnnotationsObject.class, new DefaultXstreamStrategy());
        assertEquals("<![CDATA[test&]]>", object.getName());
    }

    @Test(expected = InvocationTargetException.class)
    public void testNewInstance() throws Exception {
        assertInvokeCanNotInstanceClassConstructor(XmlUtils.class, "Utility class");
    }

    @Test
    public void testUnparsedCharacterData() {
        assertEquals("<![CDATA[123]]>", XmlUtils.unparsedCharacterData("123"));
    }

    private static final String PRETTY_MAP_XML = "<map>\n" + //
            "  <entry>\n" + //
            "    <string>a</string>\n" + //
            "    <string>1</string>\n" + //
            "  </entry>\n" + //
            "  <entry>\n" + //
            "    <string>b</string>\n" + //
            "    <string>2</string>\n" + //
            "  </entry>\n" + //
            "</map>";

    @Test
    public void testToXmlOfMap() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        assertEquals(PRETTY_MAP_XML, XmlUtils.toXml(map));
    }

    @Test
    public void testFromXmlOfMap() {
        @SuppressWarnings("unchecked")
        Map<String, String> map = XmlUtils.fromXml(PRETTY_MAP_XML, Map.class);
        assertThat(map.size(), is(2));
        assertEquals("1", map.get("a"));
        assertEquals("2", map.get("b"));
    }

}
