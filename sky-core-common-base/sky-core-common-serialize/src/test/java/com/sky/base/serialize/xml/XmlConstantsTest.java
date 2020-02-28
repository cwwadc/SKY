package com.sky.base.serialize.xml;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.sky.base.serialize.AbstractSerializeTest;
import com.sky.base.serialize.xml.XmlConstants;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-12
 */
public class XmlConstantsTest extends AbstractSerializeTest {

    @Test(expected = InvocationTargetException.class)
    public void testNewInstance() throws Exception {
        assertInvokeCanNotInstanceClassConstructor(XmlConstants.class, "Constant class");
    }
}
