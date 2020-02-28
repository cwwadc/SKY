package com.sky.base.jdbc.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author dengny
 * @version 1.0.0
 * @Title GenericDao.java
 * @Description
 * @date 2019-03-25
 */
public interface GenericDao<E extends Entity> {
	void add(E arg0);

	void add(String arg0, E arg1);

	void delete(Serializable arg0);

	void delete(String arg0, Object... arg1);

	List<E> getAll();

	E get(Serializable arg0);

	List query(String arg0, Object... arg1);

	List query(String arg0, int arg1, int arg2, Object... arg3);

	Object queryOne(String arg0, Object... arg1);

	void update(String arg0, Object... arg1);

	Map getMap(String arg0, Object... arg1);

	void batchUpdate(List<E> arg0);

	void batchInsert(String arg0, List<E> arg1);

	void batchDeleteById(List<Serializable> arg0);
}
