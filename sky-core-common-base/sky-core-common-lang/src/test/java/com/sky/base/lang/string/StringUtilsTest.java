package com.sky.base.lang.string;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.sky.base.lang.AbstractUtilsTest;
import com.sky.base.lang.string.StringUtils;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-06
 */
public class StringUtilsTest extends AbstractUtilsTest {

	@Test(expected = InvocationTargetException.class)
	public void testNewInstance() throws Exception {
		assertInvokeUtilityClassConstructor(StringUtils.class, "Utility class");
	}
}
