package com.sky.base.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-06
 */
public abstract class AbstractUtilsTest {

	protected void assertInvokeUtilityClassConstructor(Class<?> c, String errorMessage) throws Exception {
		try {
			Constructor<?> constructor = c.getDeclaredConstructor();
			constructor.setAccessible(true);
			constructor.newInstance();
		} catch (Exception e) {
			assertTrue(IllegalStateException.class.isAssignableFrom(e.getCause().getClass()));
			assertEquals(errorMessage, e.getCause().getMessage());
			throw e;
		}
	}

}
