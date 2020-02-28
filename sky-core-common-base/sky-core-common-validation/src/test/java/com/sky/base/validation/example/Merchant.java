package com.sky.base.validation.example;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sky.base.validation.metadata.OptionalStringValue;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-27
 */
public class Merchant {

	@NotNull(message = "商户编号不能为空")
	@Min(value = 1L, message = "商户编号不能小于等于0")
	private Long id;
	@NotBlank(message = "商户名称不能为空白")
	private String name;
	@OptionalStringValue(values = { "01", "02" }, message = "商户类型取值无效")
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Merchant [id=" + id + ", name=" + name + ", type=" + type + "]";
	}

}
