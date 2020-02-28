package com.sky.base.jdbc.mybatis;

import java.util.List;

/**
 * 
 * @Title Mapper工具类
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-03-02
 */
public final class MapperUtils {

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isEmpty(String str) {
		return isNull(str) || str.length() == 0;
	}

	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}

	public static boolean isNull(Object obj) {
		return obj == null;
	}

	public static boolean isNotEmpty(List<Object> objects) {
		return !isEmpty(objects);
	}

	public static boolean isEmpty(List<Object> objects) {
		return isNull(objects) || objects.isEmpty();
	}

}
