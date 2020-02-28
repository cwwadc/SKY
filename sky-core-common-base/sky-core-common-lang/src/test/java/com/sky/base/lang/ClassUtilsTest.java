package com.sky.base.lang;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sky.base.lang.ClassUtils;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-26
 */
public class ClassUtilsTest {

	@Test
	public void testIsPrimitiveClassObject() {
		assertTrue(ClassUtils.isPrimitiveClass(1));
		assertTrue(ClassUtils.isPrimitiveClass(2L));
		assertTrue(ClassUtils.isPrimitiveClass((short) 3));
		assertTrue(ClassUtils.isPrimitiveClass((byte) 4));
		assertTrue(ClassUtils.isPrimitiveClass((char) 5));
		assertTrue(ClassUtils.isPrimitiveClass(1.2f));
		assertTrue(ClassUtils.isPrimitiveClass(2.23d));
		assertTrue(ClassUtils.isPrimitiveClass(false));
		assertFalse(ClassUtils.isPrimitiveClass("123"));
		assertFalse(ClassUtils.isPrimitiveClass(new Object()));
		assertFalse(ClassUtils.isPrimitiveClass(new MockObject()));
	}

	@Test
	public void testIsPrimitiveClassClassOfQ() {
		assertTrue(ClassUtils.isPrimitiveClass(Integer.class));
		assertTrue(ClassUtils.isPrimitiveClass(Long.class));
		assertTrue(ClassUtils.isPrimitiveClass(Short.class));
		assertTrue(ClassUtils.isPrimitiveClass(Byte.class));
		assertTrue(ClassUtils.isPrimitiveClass(Character.class));
		assertTrue(ClassUtils.isPrimitiveClass(Float.class));
		assertTrue(ClassUtils.isPrimitiveClass(Double.class));
		assertTrue(ClassUtils.isPrimitiveClass(Boolean.class));
		assertFalse(ClassUtils.isPrimitiveClass(String.class));
		assertFalse(ClassUtils.isPrimitiveClass(Object.class));
		assertFalse(ClassUtils.isPrimitiveClass(MockObject.class));
	}

	@Test
	public void testIsPrimitiveClassOrStringObject() {
		assertTrue(ClassUtils.isPrimitiveClassOrString(1));
		assertTrue(ClassUtils.isPrimitiveClassOrString(2L));
		assertTrue(ClassUtils.isPrimitiveClassOrString((short) 3));
		assertTrue(ClassUtils.isPrimitiveClassOrString((byte) 4));
		assertTrue(ClassUtils.isPrimitiveClassOrString((char) 5));
		assertTrue(ClassUtils.isPrimitiveClassOrString(1.2f));
		assertTrue(ClassUtils.isPrimitiveClassOrString(2.23d));
		assertTrue(ClassUtils.isPrimitiveClassOrString(false));
		assertTrue(ClassUtils.isPrimitiveClassOrString("123"));
		assertFalse(ClassUtils.isPrimitiveClassOrString(new Object()));
		assertFalse(ClassUtils.isPrimitiveClassOrString(new MockObject()));
	}

	@Test
	public void testIsPrimitiveClassOrStringClassOfQ() {
		assertTrue(ClassUtils.isPrimitiveClassOrString(Integer.class));
		assertTrue(ClassUtils.isPrimitiveClassOrString(Long.class));
		assertTrue(ClassUtils.isPrimitiveClassOrString(Short.class));
		assertTrue(ClassUtils.isPrimitiveClassOrString(Byte.class));
		assertTrue(ClassUtils.isPrimitiveClassOrString(Character.class));
		assertTrue(ClassUtils.isPrimitiveClassOrString(Float.class));
		assertTrue(ClassUtils.isPrimitiveClassOrString(Double.class));
		assertTrue(ClassUtils.isPrimitiveClassOrString(Boolean.class));
		assertTrue(ClassUtils.isPrimitiveClassOrString(String.class));
		assertFalse(ClassUtils.isPrimitiveClassOrString(Object.class));
		assertFalse(ClassUtils.isPrimitiveClassOrString(MockObject.class));
	}

	private static class MockObject {

	}
}
