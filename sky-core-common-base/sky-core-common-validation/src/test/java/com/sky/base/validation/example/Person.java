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
public class Person {

	@NotBlank(message = "姓名不能为空")
	private String name;
	@NotBlank(message = "备注不能为空", groups = { Group1.class })
	@Size(min = 2, max = 10, message = "备注长度不符, 长度要求2~10", groups = { Group1.class })
	@NotBlank(message = "备注不能为空2", groups = { Group2.class })
	@Size(min = 3, max = 7, message = "备注长度不符, 长度要求3~7", groups = { Group2.class })
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static interface Group1 {

	}

	public static interface Group2 {

	}
}
