package com.sky.base.serialize.xml.driver;

import static org.junit.Assert.*;

import java.io.StringWriter;

import org.junit.Test;

import com.sky.base.serialize.xml.driver.ConfigurableXppDriver;
import com.sky.base.serialize.xml.driver.ConfigurableXppDriver.CompactUnparsedCharacterDataPrintWriter;
import com.sky.base.serialize.xml.driver.ConfigurableXppDriver.PrettyUnparsedCharacterDataPrintWriter;
import com.sky.base.serialize.xml.driver.ConfigurableXppDriver.UnparsedCharacterDataTypeEnum;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-12
 */
public class ConfigurableXppDriverTest {

    @Test
    public void testCreatePrettyWriter() {
        ConfigurableXppDriver driver = new ConfigurableXppDriver(false, UnparsedCharacterDataTypeEnum.NO_CDATA,
                new NoNameCoder());
        HierarchicalStreamWriter writer = driver.createWriter(new StringWriter());
        assertTrue(PrettyUnparsedCharacterDataPrintWriter.class.isAssignableFrom(writer.getClass()));
    }

    @Test
    public void testCreateCompactWriter() {
        ConfigurableXppDriver driver = new ConfigurableXppDriver(true, UnparsedCharacterDataTypeEnum.CONDITION_CDATA,
                new NoNameCoder());
        HierarchicalStreamWriter writer = driver.createWriter(new StringWriter());
        assertTrue(CompactUnparsedCharacterDataPrintWriter.class.isAssignableFrom(writer.getClass()));
    }

    @Test
    public void testNoCDataWriteText() {
        ConfigurableXppDriver driver = new ConfigurableXppDriver(true, UnparsedCharacterDataTypeEnum.NO_CDATA,
                new NoNameCoder());
        assertWriteText(driver, "123", "123");
        assertWriteText(driver, "123<", "123&lt;");
        assertWriteText(driver, "<![CDATA[123]]>", "&lt;![CDATA[123]]&gt;");
    }

    @Test
    public void testAllCDataWriteText() {
        ConfigurableXppDriver driver = new ConfigurableXppDriver(true, UnparsedCharacterDataTypeEnum.ALL_CDATA,
                new NoNameCoder());
        assertWriteText(driver, "123", "<![CDATA[123]]>");
        assertWriteText(driver, "123<", "<![CDATA[123<]]>");
        assertWriteText(driver, "<![CDATA[123]]>", "<![CDATA[<![CDATA[123]]>]]>");
    }

    @Test
    public void testConditionCDataWriteText() {
        ConfigurableXppDriver driver = new ConfigurableXppDriver(true, UnparsedCharacterDataTypeEnum.CONDITION_CDATA,
                new NoNameCoder());

        assertWriteText(driver, "123", "123");
        assertWriteText(driver, "123<", "123&lt;");
        assertWriteText(driver, "<![CDATA[123]]>", "<![CDATA[123]]>");
        assertWriteText(driver, "<![CDATA[123<]]>", "<![CDATA[123<]]>");
    }

    private void assertWriteText(ConfigurableXppDriver driver, String text, String expectedText) {
        StringWriter out = new StringWriter();
        QuickWriter outWraper = new QuickWriter(out);
        PrettyUnparsedCharacterDataPrintWriter writer = (PrettyUnparsedCharacterDataPrintWriter) driver
                .createWriter(out);
        writer.writeText(outWraper, text);
        writer.flush();
        outWraper.flush();
        assertEquals(expectedText, out.getBuffer().toString());
    }

}
