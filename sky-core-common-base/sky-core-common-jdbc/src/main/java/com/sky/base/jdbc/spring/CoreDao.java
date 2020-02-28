package com.sky.base.jdbc.spring;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.jdbc.support.KeyHolder;

import com.sky.base.jdbc.spring.annotation.Table;
import com.sky.base.lang.string.StringUtils;

/**
 * 
 * @Title
 * @Description
 * @author allinpay
 * @author lizp
 * @version 1.0.0
 * @date 2019-04-02
 */
public class CoreDao extends BaseJdbcAccessor implements BeanNameAware, BeanFactoryAware {

	private static Map<Class<?>, String> tableNameMap = new ConcurrentHashMap<>();

	private static Map<Class<?>, String[]> tableKeysMap = new ConcurrentHashMap<>();

	private static String beanName;

	private static BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		CoreDao.beanFactory = beanFactory;
	}

	@Override
	public void setBeanName(String beanName) {
		CoreDao.beanName = beanName;
	}

	public static CoreDao getInstance() {
		return (CoreDao) beanFactory.getBean(beanName);
	}

	public <T> List<T> search(String sql, Class<T> clz, Object... args) {
		return DbUtils.getList(jdbcTemplate, sql, clz, args);
	}

	public <T> List<T> searchPage(String sql, Class<T> clz, int start, int size, Object... args) {
		return DbUtils.getPage(jdbcTemplate, sql, clz, start, size, args);
	}

	public List<Object[]> searchForArray(String sql, Object... args) {
		return DbUtils.getListForArray(jdbcTemplate, sql, args);
	}

	public List<Object[]> searchForArrayPage(String sql, int start, int size, Object... args) {
		return DbUtils.getListForArrayPage(jdbcTemplate, sql, start, size, args);
	}

	public List<Map<String, Object>> searchForMap(String sql, Object... args) {
		return DbUtils.getListForMap(jdbcTemplate, sql, args);
	}

	public List<Map<String, Object>> searchForMapPage(String sql, int start, int size, Object... args) {
		return DbUtils.getListForMapPage(jdbcTemplate, sql, start, size, args);
	}

	public int count(String sql, Object... args) {
		return DbUtils.count(jdbcTemplate, sql, args);
	}

	public <T> T getObject(String sql, Class<T> clz, Object... args) {
		return DbUtils.getObj(jdbcTemplate, sql, clz, args);
	}

	public <T> T getSimpleObject(String sql, Class<T> clz, Object... args) {
		return DbUtils.getSimpleObj(jdbcTemplate, sql, clz, args);
	}

	public Long getSeq(String seqname) {
		return Long.valueOf(DbUtils.getSeq(jdbcTemplate, seqname));
	}

	public String getTableName(Object obj) {
		if (obj == null) {
			return null;
		}
		return getTableNameEx(obj.getClass());
	}

	private <T> String getTableNameEx(Class<T> clz) {
		if (CoreDao.tableNameMap.containsKey(clz)) {
			return (String) CoreDao.tableNameMap.get(clz);
		}
		return readTableName(clz);
	}

	private <T> String readTableName(Class<T> clz) {
		Table table = clz.getAnnotation(Table.class);
		if (!Optional.ofNullable(table).isPresent()) {
			logger.error("The class is missing the table annotation");
			return null;
		}
		String tableName = table.name();
		if (StringUtils.isBlank(tableName)) {
			logger.error("The class is missing the table name attribute");
			return null;
		}
		tableNameMap.put(clz, tableName);
		return tableName;
	}

	public <T> String getTableName(Class<T> c) {
		if (c == null) {
			return null;
		}
		if (CoreDao.tableNameMap.containsKey(c)) {
			return (String) CoreDao.tableNameMap.get(c);
		}
		return readTableName(c);
	}

	public String[] getTableKeys(Object obj) {
		if (obj == null) {
			return null;
		}
		Class<? extends Object> clz = obj.getClass();
		if (CoreDao.tableKeysMap.containsKey(clz)) {
			return (String[]) CoreDao.tableKeysMap.get(clz);
		}

		Table table = clz.getAnnotation(Table.class);
		if (!Optional.ofNullable(table).isPresent()) {
			logger.error("The class is missing the table annotation");
			return null;
		}
		String[] keys = table.primary();
		if (keys != null && keys.length > 0) {
			tableKeysMap.put(clz, keys);
		} else {
			logger.error("The class is missing the table primary attribute");
		}
		return keys;
	}

	public int add(Object obj) {
		String tableName = getTableName(obj);
		if (tableName != null) {
			return DbUtils.insertObj(true, jdbcTemplate, tableName, obj);
		}
		return 0;
	}

	public int add(Object obj, String tableName) {
		if (tableName != null) {
			return DbUtils.insertObj(true, jdbcTemplate, tableName, obj);
		}
		return 0;
	}

	public int addEx(Object obj, boolean isFreshCache) {
		String tableName = getTableNameEx(obj, isFreshCache);
		if (tableName != null) {
			return DbUtils.insertObj(true, jdbcTemplate, tableName, obj);
		}
		return 0;
	}

	public String getTableNameEx(Object obj, boolean isFreshCache) {
		if (obj == null) {
			return null;
		}
		return getTableNameExCache(obj.getClass(), isFreshCache);
	}

	public String getTableNameExCache(Class<?> clz, boolean isFreshCache) {
		if (!isFreshCache) {
			if (CoreDao.tableNameMap.containsKey(clz)) {
				return (String) CoreDao.tableNameMap.get(clz);
			}
		}
		return readTableName(clz);
	}

	public int add(String sql, Object... args) {
		return DbUtils.updateEx(jdbcTemplate, sql, args);
	}

	public KeyHolder add(Object obj, String... retCols) {
		String tableName = getTableName(obj);
		if (tableName != null) {
			return DbUtils.insertObj(true, jdbcTemplate, tableName, obj, retCols);
		}
		return null;
	}

	public int update(Object obj) {
		String tableName = getTableName(obj);
		if (tableName != null) {
			return DbUtils.updateObjFull(true, jdbcTemplate, tableName, obj.getClass(), obj, getTableKeys(obj));
		}
		return 0;
	}

	public int update(Object obj, int flag) {
		String tableName = getTableName(obj);
		if (tableName != null) {
			return DbUtils.updateObj(true, jdbcTemplate, tableName, obj.getClass(), obj, flag, getTableKeys(obj));
		}
		return 0;
	}

	public int[] updateBatch(String sql, Object[]... params) {
		return DbUtils.updateBatch(jdbcTemplate, sql, params);
	}

	public KeyHolder update(final String sql, final Object[] args, final String[] retCols) {
		return DbUtils.update(jdbcTemplate, sql, args, retCols);
	}

	@Override
	public int delete(String sql, Object... args) {
		return DbUtils.delete(jdbcTemplate, sql, args);
	}

	public int delete(Object obj) {
		String tableName = getTableName(obj);
		if (tableName != null) {
			return DbUtils.deleteObj(true, jdbcTemplate, tableName, obj, getTableKeys(obj));
		}
		return 0;
	}

	public int delete(Object obj, String tableName) {
		if (tableName != null) {
			return DbUtils.deleteObj(true, jdbcTemplate, tableName, obj, getTableKeys(obj));
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	public <T> T find(T obj, Class<T> clz) {
		if (clz == null) {
			clz = (Class<T>) obj.getClass();
		}
		String tableName = getTableName(clz);
		if (tableName != null) {
			return clz.cast(DbUtils.findObj(true, jdbcTemplate, tableName, obj, clz, getTableKeys(obj)));
		}
		return null;
	}

	public <T> T find(Class<T> clz, String tableName, String... args) {
		if (tableName == null) {
			tableName = getTableName(clz);
		}
		if (tableName != null) {
			return clz.cast(DbUtils.findObj(jdbcTemplate, tableName, clz, args));
		}
		return null;
	}

	public <T> T findList(Class<T> clz, String tableName, String... args) {
		if (tableName == null) {
			tableName = getTableName(clz);
		}
		if (tableName != null) {
			return clz.cast(DbUtils.findList(jdbcTemplate, tableName, clz, args));
		}
		return null;
	}

	public int[] addBatch(Object... objects) {
		String tableName = null;
		if (objects == null || objects.length == 0) {
			return null;
		} else {
			tableName = getTableName(objects[0]);
		}
		if (tableName != null) {
			return DbUtils.insertObjs(true, jdbcTemplate.getDataSource(), tableName, objects);
		}
		return null;
	}

}
