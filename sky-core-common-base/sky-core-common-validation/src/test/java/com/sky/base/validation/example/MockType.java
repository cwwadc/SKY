package com.sky.base.validation.example;

import com.sky.base.validation.metadata.OptionalStringValue;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-27
 */
public class MockType {

	@OptionalStringValue(values = { "01", "02" }, message = "类型取值无效")
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "MockType [type=" + type + "]";
	}

}
