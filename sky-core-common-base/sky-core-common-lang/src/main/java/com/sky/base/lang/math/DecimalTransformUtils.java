package com.sky.base.lang.math;

/**
 * @author zzq
 * @version 1.0.0
 * @Title {@link}
 * @Description 进制转换工具类
 * @date 2019/3/14
 */
public class DecimalTransformUtils {

	/**
	 * 10进制
	 */
	public static final String DATASOURCE_10 = "0123456789";
	/**
	 * 16进制
	 */
	public static final String DATASOURCE_16 = "0123456789ABCDEF";
	/**
	 * 36进制
	 */
	public static final String DATASOURCE_36 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String transform(String randomDatasource, Long id) {
		int randomDatasourceLength = randomDatasource.length();
		StringBuilder param = new StringBuilder();
		int index = 0;
		while (id > (randomDatasourceLength - 1)) {
			index = (int) (id % randomDatasourceLength);
			param.append(randomDatasource.charAt(index));
			id = id / randomDatasourceLength;
		}
		param.append(randomDatasource.charAt(id.intValue()));
		return param.reverse().toString();
	}

	public static Long reduction(String randomDatasource, String param) {
		int randomDatasourceLength = randomDatasource.length();
		char randomDatasourceDefault = randomDatasource.substring(0, 1).toCharArray()[0];
		long num = 0;
		int index = 0;
		for (int i = 0, length = param.length(); i < length; i++) {
			char str = param.charAt(i);
			if (randomDatasourceDefault == str) {
				continue;
			}
			index = randomDatasource.indexOf(str);
			num += index * (Math.pow(randomDatasourceLength, (length - i - 1)));
		}
		return num;
	}
}
