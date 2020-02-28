package com.sky.base.lang.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

/***
 * 数值工具类
 * @author dengny
 * @version 1.0.0
 * @Title MathUtils
 * @Description
 * @date 2019-03-06
 */
public class MathUtils {
	/**
	 * 乘法
	 * @param number 因数
	 * @param times 倍数
	 * @return BigDecimal
	 */
	public static BigDecimal multiply(BigDecimal number,BigDecimal times) {
		return  number.multiply(times);
	}
	/**
	 * 乘法
	 * @param number 因数
	 * @param int 倍数
	 * @return BigDecimal
	 */
	public static Long multiply(String number,int times) {
		return  new BigDecimal(number).multiply(new BigDecimal(times)).longValueExact();
	}
	/***
	 * 除法
	 * 四舍五入，保留两位小数
	 * @param dividend 被除数
	 * @param divisor 除数
	 * @return Double
	 */
	public static BigDecimal divide(BigDecimal dividend,BigDecimal divisor) {
		return  divide(dividend,divisor,2);
	}
	/***
	 * 除法
	 * 四舍五入
	 * @param dividend 被除数
	 * @param divisor 除数
	 * @param newScale 小数位
	 * @return 
	 */
	public static BigDecimal divide(BigDecimal dividend,BigDecimal divisor,int newScale) {
		return  divide(dividend,divisor,newScale, RoundingMode.HALF_UP);
	}
	/**
	 * 除法
	 * @param dividend 被除数
	 * @param divisor 除数
	 * @param newScale 小数点精确度
	 * @param model 取余方法
	 * @return 
	 */
	public static BigDecimal divide(BigDecimal dividend,BigDecimal divisor,int newScale,RoundingMode model) {
		return  dividend.divide(divisor).setScale(newScale, model);
	}
	/***
	 * 加法
	 * @param number1  
	 * @param number2
	 * @return BigDecimal
	 */
	public static BigDecimal add(BigDecimal number1,BigDecimal number2) {
		return  number1.add(number2);
	}
	/***
	 * 减法
	 * @param number1 被减数
	 * @param number2 减数
	 * @return BigDecimal
	 */
	public static BigDecimal subtract(BigDecimal number1,BigDecimal number2) {
		return  number1.subtract(number2);
	}
	/**
	 * 绝对值
	 * @param number
	 * @return
	 */
	public static BigDecimal abs(BigDecimal number) {
		return number.abs();
	}
	
}
