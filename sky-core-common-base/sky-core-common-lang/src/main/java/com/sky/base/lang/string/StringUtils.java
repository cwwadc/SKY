package com.sky.base.lang.string;

/**
 * @Title 字符串操作工具类
 * @Description
 * @author dengny
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-13
 */
public final class StringUtils extends org.apache.commons.lang3.StringUtils {

	private StringUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 全角
	 * 
	 * @param input
	 * @return
	 */
	public static String sbcCase(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; ++i) {
			if (c[i] == 32) {
				c[i] = 12288;
			} else if (c[i] < 127) {
				c[i] += 'ﻠ';
			}
		}
		return new String(c);
	}

	/**
	 * 半角
	 * 
	 * @param input
	 * @return
	 */
	public static String dbcCase(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; ++i) {
			if (c[i] == 12288) {
				c[i] = 32;
			} else if (c[i] > '＀' && c[i] < '｟') {
				c[i] -= 'ﻠ';
			}
		}
		return new String(c);
	}

}
