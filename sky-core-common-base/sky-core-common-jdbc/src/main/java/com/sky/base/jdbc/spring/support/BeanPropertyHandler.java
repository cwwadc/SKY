package com.sky.base.jdbc.spring.support;

/**
 * @author allinpay
 */
public interface BeanPropertyHandler {
	/**
	 * handle
	 * 
	 * @param bean
	 * @param propName
	 * @param val
	 * @return
	 */
	public boolean handle(Object bean, String propName, Object val);
}
