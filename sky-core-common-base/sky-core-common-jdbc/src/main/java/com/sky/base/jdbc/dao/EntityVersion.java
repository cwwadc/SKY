package com.sky.base.jdbc.dao;

import java.io.Serializable;

/**
 * @author dengny
 * @version 1.0.0
 * @Title EntityVersion.java
 * @Description
 * @date 2019-03-25
 */
public interface EntityVersion<IDClass extends Serializable> extends Entity<IDClass> {
	void setVersion(Long arg0);

	Long getVersion();
}
