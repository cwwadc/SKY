package com.sky.base.jdbc.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author dengny
 * @version 1.0.0
 * @Title GenericUtils.java
 * @Description
 * @date 2019-03-25
 */
public class GenericUtils {
	public static Class getGenericClass(Class clazz, int index) {
		
		Type genType = clazz.getGenericSuperclass();
		if (genType instanceof ParameterizedType) {
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
			if (params != null && params.length > index) {
				return (Class) params[index];
			}
		}

		return null;
	}

	public static Class getGenericClass(Class clazz) {
		return getGenericClass(clazz, 0);
	}

}
