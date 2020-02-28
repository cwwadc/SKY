package com.sky.base.jdbc.spring.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @title
 * @description
 * @author lizp
 * @version 1.0.0
 * @date 2019-06-25
 */
@Retention(RUNTIME)
@Target(TYPE)
@Documented
public @interface Table {

	/**
	 * 表名
	 * 
	 * @return
	 */
	String name();

	/**
	 * 主键
	 * 
	 * @return
	 */
	String[] primary();

}
