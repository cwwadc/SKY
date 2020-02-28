package com.sky.base.jdbc.mybatis;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;

/**
 * 
 * @title
 * @description
 * @author lizp
 * @version 1.0.0
 * @date 2019-05-07
 */
public class BatchSqlSessionTemplate implements InitializingBean {

	private SqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		if (sqlSessionTemplate == null) {
			this.sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
		}
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public void afterPropertiesSet() {
		notNull(sqlSessionTemplate, "A SqlSessionFactory or a SqlSessionTemplate is required.");
		isTrue(ExecutorType.BATCH == sqlSessionTemplate.getExecutorType(),
		        "SqlSessionTemplate's executor type must be BATCH");
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

}
