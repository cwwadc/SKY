package com.sky.base.lang.regular;

import java.util.regex.Pattern;
/***
 * @Title 正则校验工具类
 * @Description
 * @author dengny
 * @version 1.0.0
 * @date 2018-12-13
 */
public class RegularUtils {
	public final static Pattern IS_INTEGER = Pattern.compile("^[-\\+]?[\\d]*$");
	public final static Pattern IS_DOUBLE = Pattern.compile("^[-\\+]?[.\\d]*$");
	public final static Pattern	IS_EMAIL = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
	public final static Pattern IS_MOBILE = Pattern.compile("^1[3|4|5|8|7][0-9]{9}$");
	public final static Pattern IS_PHONE = Pattern.compile("^0[0-9]{2,3}[-|－][0-9]{7,8}([-|－][0-9]{1,4})?$");
	public final static Pattern IS_IPV4 = Pattern.compile("^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$");
	public final static Pattern IS_MAC = Pattern.compile("^([0-9a-fA-F]{2})(([\\s:-][0-9a-fA-F]{2}){5})$");
	public final static Pattern IS_NUMBER = Pattern.compile("^\\d+(\\.\\d+)?$");

    public static final Pattern IS_TWO_DECIMAL_PLACES = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");

	public static boolean isInteger(String str) {
		return IS_INTEGER.matcher(str).matches();
	}

	public static boolean isDouble(String str) {
		return IS_DOUBLE.matcher(str).matches();
	}
	public static boolean isEmail(String str) {
		return IS_EMAIL.matcher(str).matches();
	}
	public static boolean isMobile(String mobile) {
		return IS_MOBILE.matcher(mobile).matches();
	}

	public static boolean isPhone(String phone) {
		return IS_PHONE.matcher(phone).matches();
	}

	public static boolean isIPV4(String ip) {
		return IS_IPV4.matcher(ip).matches();
	}
	public static boolean isMac(String mac) {
		return IS_MAC.matcher(mac).matches();
	}

	public static boolean isNumber(String str) {
		return IS_NUMBER.matcher(str).matches();
	}

    public static boolean isTwoDecimalPlaces(String str) {
        return IS_TWO_DECIMAL_PLACES.matcher(str).matches();
    }

}
