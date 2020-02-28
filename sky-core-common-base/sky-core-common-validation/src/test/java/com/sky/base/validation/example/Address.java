package com.sky.base.validation.example;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-27
 */
public class Address {

	@NotBlank(message = "所属省不能为空")
	private String province;
	@NotBlank(message = "所属市不能为空")
	private String city;
	@Size(max = 20, message = "所属街道字段长度不能大于20")
	private String street;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public String toString() {
		return "Address [province=" + province + ", city=" + city + ", street=" + street + "]";
	}

}
