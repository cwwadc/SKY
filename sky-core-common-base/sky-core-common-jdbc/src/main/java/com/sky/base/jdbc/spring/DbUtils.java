package com.sky.base.jdbc.spring;

import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.sql.DataSource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.sky.base.jdbc.spring.support.BeanPropRowMap;
import com.sky.base.jdbc.spring.support.BeanTools;

/**
 * 
 * @Title
 * @Description
 * @author allinpay
 * @author lizp
 * @version 1.0.0
 * @date 2019-04-02
 */
public class DbUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbUtils.class);

	/** 忽略null和空字符串 */
	public static final int IG_NULL = 1;
	/** 忽略0 */
	public static final int IG_ZERO = 2;
	/** 忽略-1 */
	public static final int IG_NEGATIVE = 4;
	/** 忽略该类型最小的 */
	public static final int IG_MIN = 8;
	/** 忽略null但不忽略空字符串 */
	public static final int IG_NULLNOTBLANK = 16;

	private PropertyDescriptor[] pdas;
	private String[] keys;

	public static <T extends Object> int updateObjCond(boolean undercore, JdbcOperations dbop, String tab,
	        Class<T> clzz, Object obj, String... keys) {
		ArrayList<Object> ls = new ArrayList<Object>();
		String sql = createUpdateSql(undercore, tab, clzz, obj, ls, IG_NULL | IG_MIN, keys);
		return updateEx(dbop, sql, ls.toArray());
	}

	public static int updateObjFull(JdbcOperations dbop, String tab, Class<? extends Object> clzz, Object obj,
	        String... keys) {
		return updateObjFull(false, dbop, tab, clzz, obj, keys);
	}

	public static int updateObjFull(boolean undercore, JdbcOperations dbop, String tab, Class<? extends Object> clzz,
	        Object obj, String... keys) {
		ArrayList<Object> ls = new ArrayList<Object>();
		String sql = createUpdateSql(undercore, tab, clzz, obj, ls, IG_NULLNOTBLANK | IG_MIN, keys);
		return updateEx(dbop, sql, ls.toArray());
	}

	public static int updateObj(boolean undercore, JdbcOperations dbop, String tab, Class<? extends Object> clzz,
	        Object obj, int flags, String... keys) {
		ArrayList<Object> ls = new ArrayList<Object>();
		String sql = createUpdateSql(undercore, tab, clzz, obj, ls, flags, keys);
		return updateEx(dbop, sql, ls.toArray());
	}

	public static int updateEx(JdbcOperations dbop, String sql, Object... args) {
		logSql(sql, args);
		return dbop.update(sql, args);
	}

	public static int[] updateBatch(JdbcOperations dbop, String sql, Object[]... objs) {
		return dbop.batchUpdate(sql, Arrays.asList(objs));
	}

	public static KeyHolder updateX(JdbcOperations dbop, final String sql, final String[] retCols,
	        final Object... args) {
		return update(dbop, sql, args, retCols);
	}

	public static KeyHolder update(JdbcOperations dbop, final String sql, final Object[] args, final String[] retCols) {
		logSql(sql, args);
		KeyHolder kh = new GeneratedKeyHolder();
		dbop.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				int i = 1;
				PreparedStatement ps;
				ps = con.prepareStatement(sql, retCols);
				for (Object o : args) {
					StatementCreatorUtils.setParameterValue(ps, i++, SqlTypeValue.TYPE_UNKNOWN, o);
				}
				return ps;
			}
		}, kh);
		return kh;
	}

	public static <T> List<T> getList(JdbcOperations dbop, String sql, Class<T> clz, Object... args) {
		BeanPropRowMap<T> mp = new BeanPropRowMap<>(clz);
		mp.setPrimitivesDefaultedForNullValue(true);
		logSql(sql, args);
		return dbop.query(sql, args, mp);
	}

	public static <T> List<T> getPage(JdbcOperations dbop, String sql, Class<T> clz, int start, int size,
	        Object... args) {
		sql = "select * from (select rownum myrownum,newtable.* from (" + sql + ") newtable where rownum <= "
		        + (start + size) + ") where myrownum > " + start;
		BeanPropRowMap<T> mp = new BeanPropRowMap<>(clz);
		mp.setPrimitivesDefaultedForNullValue(true);
		logSql(sql, args);
		return dbop.query(sql, args, mp);
	}

	public static List<Object[]> getListForArray(JdbcOperations dbop, String sql, Object... args) {
		logSql(sql, args);
		List<Map<String, Object>> list = dbop.queryForList(sql, args);
		List<Object[]> data = new ArrayList<Object[]>();
		for (Map<String, Object> map : list) {
			List<Object> record = new ArrayList<Object>();
			Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
			// 封装数据
			while (it.hasNext()) {
				Map.Entry<String, Object> entry = it.next();
				// 只保留值,非key
				record.add(entry.getValue());
			}
			data.add(record.toArray());
		}
		return data;
	}

	public static List<Object[]> getListForArrayPage(JdbcOperations dbop, String sql, int start, int size,
	        Object... args) {
		sql = "select * from (select newtable.*,rownum myrownum from (" + sql + ") newtable where rownum <= "
		        + (start + size) + ") where myrownum > " + start;
		return getListForArray(dbop, sql, args);
	}

	public static List<Map<String, Object>> getListForMap(JdbcOperations dbop, String sql, Object... args) {
		logSql(sql, args);
		return dbop.queryForList(sql, args);
	}

	public static List<Map<String, Object>> getListForMapPage(JdbcOperations dbop, String sql, int start, int size,
	        Object... args) {
		sql = "select * from (select rownum myrownum,newtable.* from (" + sql + ") newtable where rownum <= "
		        + (start + size) + ") where myrownum > " + start;
		return getListForMap(dbop, sql, args);
	}

	public static <T> T getObj(JdbcOperations dbop, String sql, Class<T> clz, Object... args) {
		BeanPropRowMap<T> mp = new BeanPropRowMap<>(clz);
		mp.setPrimitivesDefaultedForNullValue(true);
		logSql(sql, args);
		List<T> ls = dbop.query(sql, args, mp);
		if (ls.isEmpty()) {
			return null;
		}
		return ls.iterator().next();
	}

	public static int count(JdbcOperations dbop, String sql, Object... args) {
		sql = "select count(*) from (" + sql + ")";
		return (Integer) DbUtils.getSimpleObj(dbop, sql, Integer.class, args);
	}

	public static <T> T getSimpleObj(JdbcOperations dbop, String sql, Class<T> clz, Object... args) {
		logSql(sql, args);
		List<T> ls = dbop.queryForList(sql, args, clz);
		if (ls.isEmpty()) {
			return null;
		}
		// Iterator的next()方法,第一次next()返回的是第一个元素
		return ls.iterator().next();
	}

	public static <T> List<T> search(boolean underscore, JdbcOperations dbop, String tab, int start, int size, int flag,
	        Object startObj, Object endObj, Class<T> clz, String... ranges) {
		ArrayList<Object> ls = new ArrayList<Object>();
		String sql = createCondSqlEx(underscore, clz, startObj, endObj, ls, flag, ranges);
		return getPage(dbop, "select * from " + tab + sql, clz, start, size, ls.toArray());

	}

	public static <T> long count(boolean underscore, JdbcOperations dbop, String tab, int flag, Object startObj,
	        Object endObj, Class<T> clz, String... ranges) {
		ArrayList<Object> ls = new ArrayList<Object>();
		String sql = createCondSqlEx(underscore, clz, startObj, endObj, ls, flag, ranges);
		Long lv = (Long) getSimpleObj(dbop, "select count(*) from " + tab + sql, Long.class, ls.toArray());
		return lv.longValue();
	}

	public static <T> T findObj(JdbcOperations dbop, String tab, Class<T> clz, String... args) {
		ArrayList<String> ls = new ArrayList<String>();
		String sql = createCondSql(ls, 0, args);
		return getObj(dbop, "select * from " + tab + sql, clz, ls.toArray());
	}

	public static <T> List<T> findList(JdbcOperations dbop, String tab, Class<T> clz, String... args) {
		ArrayList<String> ls = new ArrayList<String>();
		String sql = createCondSql(ls, 0, args);
		return getList(dbop, "select * from " + tab + sql, clz, ls.toArray());
	}

	public static <T> T findObj(boolean underscore, JdbcOperations dbop, String tab, Object obj, Class<T> clz,
	        String... keys) {
		ArrayList<Object> ls = new ArrayList<Object>();
		String sql = createCondSql(underscore, obj, ls, 0, keys);
		return getObj(dbop, "select * from " + tab + sql, clz, ls.toArray());
	}

	public static int deleteObj(JdbcOperations dbop, String tab, Object obj, String... keys) {
		ArrayList<Object> ls = new ArrayList<Object>();
		String sql = createDeleteSql(false, tab, obj, ls, keys);
		return DbUtils.updateEx(dbop, sql, ls.toArray());
	}

	public static int deleteObj(boolean underscore, JdbcOperations dbop, String tab, Object obj, String... keys) {
		ArrayList<Object> ls = new ArrayList<Object>();
		String sql = createDeleteSql(underscore, tab, obj, ls, keys);
		return DbUtils.updateEx(dbop, sql, ls.toArray());
	}

	public static String createDeleteSql(boolean underscore, String tab, Object o, List<Object> ls, String... keys) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ").append(tab);
		sql.append(createCondSql(underscore, o, ls, 0, keys));
		return sql.toString();
	}

	public static int delete(JdbcOperations dbop, String sql, Object... args) {
		return DbUtils.updateEx(dbop, sql, args);
	}

	public static int[] insertObjs(DataSource ds, String tab, Object... args) {
		return insertObjs(false, ds, tab, args);
	}

	public static int[] insertObjs(boolean undercore, DataSource ds, String tab, Object... args) {
		BatchSqlUpdate bsu;
		DbUtils dbu = new DbUtils();
		List<Integer> vt = new ArrayList<>();
		dbu.init(args[0].getClass(), null);
		String sql = dbu.buildInsert(undercore, tab, vt);
		Integer[] ivt = new Integer[vt.size()];
		bsu = new BatchSqlUpdate(ds, sql, ArrayUtils.toPrimitive(vt.toArray(ivt)));
		for (Object o : args) {
			bsu.update(dbu.buildParam(undercore, o).toArray());
		}
		return bsu.flush();
	}

	public static KeyHolder insertObj(JdbcOperations dbop, String tab, Object o, String... retCol) {
		return insertObj(false, dbop, tab, o, retCol);
	}

	public static <T> KeyHolder insertObjEx(boolean undercore, JdbcOperations dbop, String tab, Class<T> clz, Object o,
	        String... retCol) {
		String sql;
		ArrayList<Object> ls = new ArrayList<Object>();
		sql = DbUtils.createInsertSql(undercore, tab, clz, o, ls, 0);
		return DbUtils.update(dbop, sql, ls.toArray(), retCol);
	}

	public static KeyHolder insertObj(boolean undercore, JdbcOperations dbop, String tab, Object o, String... retCol) {
		return insertObjEx(undercore, dbop, tab, o.getClass(), o, retCol);
	}

	public static int insertObj(JdbcOperations dbop, String tab, Object o) {
		return insertObj(false, dbop, tab, o);
	}

	public static <T> int insertObjEx(boolean undercore, JdbcOperations dbop, String tab, Class<T> clz, Object o) {
		ArrayList<Object> ls = new ArrayList<Object>();
		String sql = DbUtils.createInsertSql(undercore, tab, clz, o, ls, 1);
		return DbUtils.updateEx(dbop, sql, ls.toArray());
	}

	public static int insertObj(boolean undercore, JdbcOperations dbop, String tab, Object o) {
		return insertObjEx(undercore, dbop, tab, o.getClass(), o);
	}

	public Vector<Object> buildParam(Object o) {
		return buildParam(false, o);
	}

	public Vector<Object> buildParam(boolean underscore, Object o) {
		Vector<Object> ls = new Vector<Object>();
		List<Object> kval = null;
		if (keys != null) {
			kval = new LinkedList<Object>();
		}
		for (PropertyDescriptor pd : pdas) {
			String colName = pd.getName();
			if (isReservedWord(pd)) {
				continue;
			}
			colName = underscoreNameIfNecessary(underscore, colName);
			Object val = BeanTools.readProperty(o, pd.getReadMethod());
			if (keys != null) {
				for (String kname : keys) {
					if (kname.equalsIgnoreCase(colName)) {
						kval.add(val);
						break;
					}
				}
			}
			ls.add(val);
		}
		if (kval != null) {
			ls.addAll(kval);
		}
		return ls;
	}

	private static boolean isReservedWord(PropertyDescriptor pd) {
		return StringUtils.equals("class", pd.getName());
	}

	private static String underscoreNameIfNecessary(boolean underscore, String colName) {
		if (underscore) {
			colName = underscoreName(colName);
		}
		return colName;
	}

	public String buildInsert(String tab, List<Integer> ls) {
		return buildInsert(false, tab, ls);
	}

	public String buildInsert(boolean underscore, String tab, List<Integer> ls) {
		int i, num = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ").append(tab).append("(");
		for (PropertyDescriptor pd : pdas) {
			String colName = pd.getName();
			if (isReservedWord(pd)) {
				continue;
			}
			colName = underscoreNameIfNecessary(underscore, colName);
			ls.add(new Integer(StatementCreatorUtils.javaTypeToSqlParameterType(pd.getPropertyType())));
			sql.append(colName).append(",");
			num++;
		}
		sql.setCharAt(sql.length() - 1, ')');
		sql.append(" values (");
		for (i = 0; i < num; ++i) {
			sql.append("?,");
		}
		sql.setCharAt(sql.length() - 1, ')');
		return sql.toString();
	}

	public String buildUpdate(boolean underscore, String tab, List<Object> ls) {
		List<Object> kvl = null;
		StringBuilder where = null;
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(tab).append(" set ");
		if (keys != null) {
			where = new StringBuilder();
			where.append("where ");
			kvl = new Vector<Object>();
		}
		for (PropertyDescriptor pd : pdas) {
			String colName = pd.getName();
			if (isReservedWord(pd)) {
				continue;
			}
			colName = underscoreNameIfNecessary(underscore, colName);
			if (keys != null) {
				for (String kname : keys) {
					if (kname.equalsIgnoreCase(colName)) {
						kvl.add(StatementCreatorUtils.javaTypeToSqlParameterType(pd.getPropertyType()));
						where.append(kname).append("=? and ");
					}
				}
			}
			ls.add(StatementCreatorUtils.javaTypeToSqlParameterType(pd.getPropertyType()));
			sql.append(colName).append("=?,");
		}
		sql.setCharAt(sql.length() - 1, ' ');
		if (where != null && where.length() > 5) {
			where.delete(where.length() - 3, where.length());
			sql.append(where);
		}
		if (kvl != null) {
			ls.addAll(kvl);
		}
		return sql.toString();
	}

	public static <T> String createUpdateSql(String tab, Object o, List<T> ls, int flag, String... keys) {
		return createUpdateSql(tab, o.getClass(), ls, flag, keys);
	}

	protected static boolean appendCond(StringBuilder where, String colName, Object val, List<Object> lsv,
	        String... keys) {
		if (keys == null) {
			return false;
		}
		if (BeanTools.indexOfIg(keys, colName) == -1) {
			return false;
		}
		if (val != null) {
			where.append(colName).append("=? and ");
			lsv.add(val);
		} else {
			where.append(colName).append("is null and ");
		}
		return true;
	}

	public static String createUpdateSql(boolean underscore, String tab, Class<?> clzz, Object o, List<Object> ls,
	        int flag, String... keys) {
		List<Object> kval = null;
		StringBuilder where = null;
		StringBuilder sql = new StringBuilder();
		if (clzz == null) {
			clzz = o.getClass();
		}
		PropertyDescriptor[] pda = BeanTools.getPropertyDescriptors(clzz);
		sql.append("update ").append(tab).append(" set ");
		if (keys != null) {
			kval = new LinkedList<Object>();
			where = new StringBuilder();
			where.append("where ");
		}
		for (PropertyDescriptor pd : pda) {
			String colName = pd.getName();
			if (isReservedWord(pd)) {
				continue;
			}
			colName = underscoreNameIfNecessary(underscore, colName);
			Object val = BeanTools.readProperty(o, pd.getReadMethod());
			if (appendCond(where, colName, val, kval, keys)) {
				continue;
			}
			if (checkVal(val, flag)) {
				continue;
			}
			sql.append(colName).append("=?,");
			ls.add(val);
		}
		sql.setCharAt(sql.length() - 1, ' ');
		if (where != null && where.length() > 5) {
			where.delete(where.length() - 4, where.length());
			sql.append(where);
		}
		if (kval != null) {
			ls.addAll(kval);
		}
		return sql.toString();
	}

	public static String createInsertSql(String tab, Object o, List<Object> ls, int flag) {
		return createInsertSql(false, tab, o.getClass(), o, ls, flag);
	}

	public static String createInsertSql(boolean undercore, String tab, Object o, List<Object> ls, int flag) {
		return createInsertSql(undercore, tab, o.getClass(), o, ls, flag);
	}

	public static String underscoreName(String name) {
		return BeanTools.underscoreName(name);
	}

	public static String createInsertSql(boolean underscore, String tab, Class<?> clz, Object o, List<Object> ls,
	        int flag) {
		int i, num = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ").append(tab).append("(");
		if (clz == null) {
			clz = o.getClass();
		}
		PropertyDescriptor[] pda = BeanTools.getPropertyDescriptors(clz);
		for (PropertyDescriptor pd : pda) {
			String colName = pd.getName();
			if (isReservedWord(pd)) {
				continue;
			}
			colName = underscoreNameIfNecessary(underscore, colName);
			Object val = BeanTools.readProperty(o, pd.getReadMethod());
			if (checkVal(val, flag)) {
				continue;
			}
			sql.append(colName).append(",");
			num++;
			ls.add(val);
		}
		sql.setCharAt(sql.length() - 1, ')');
		sql.append(" values (");
		for (i = 0; i < num; ++i) {
			sql.append("?,");
		}
		sql.setCharAt(sql.length() - 1, ')');
		return sql.toString();
	}

	public static String getSeq(JdbcOperations dbop, String seqname) {
		String sql = "select " + seqname + ".nextval from dual";
		return (String) getSimpleObj(dbop, sql, String.class);
	}

	public static String buildID(JdbcOperations dbop, String seqname, int len) {
		String seq = getSeq(dbop, seqname);
		if (seq.length() > len) {
			return StringUtils.right(seq, len);
		}

		return StringUtils.leftPad(seq, len, '0');
	}

	public static String createCondSqlEx(boolean underscore, Class<?> clz, Object startObj, Object endObj,
	        List<Object> ls, int flag, String... ranges) {
		StringBuilder where = null;
		PropertyDescriptor[] pda = BeanTools.getPropertyDescriptors(clz);
		where = new StringBuilder();
		where.append(" where ");
		for (PropertyDescriptor pd : pda) {
			boolean isRange;
			Object valEnd;
			Object valStart;
			String colName;
			colName = pd.getName();
			if (isReservedWord(pd)) {
				continue;
			}
			valStart = null;
			valEnd = null;
			colName = underscoreNameIfNecessary(underscore, colName);
			if (startObj != null) {
				valStart = BeanTools.readProperty(startObj, pd.getReadMethod());
			}
			isRange = BeanTools.indexOfIg(ranges, colName) != -1;
			if (isRange) {
				valEnd = BeanTools.readProperty(endObj, pd.getReadMethod());
				if (valStart != null && !checkVal(valStart, flag)) {
					ls.add(valStart);
					where.append(colName).append(">=? and ");
				}
				if (valEnd != null && !checkVal(valEnd, flag)) {
					ls.add(valStart);
					where.append(colName).append("<? and ");
				}
			} else {
				if (valStart != null && !checkVal(valStart, flag)) {
					ls.add(valStart);
					where.append(colName).append("=? and ");
				}
			}

		}
		if (where.length() > 7) {
			where.delete(where.length() - 4, where.length());
		} else {
			where.delete(0, where.length());
		}
		return where == null ? "" : where.toString();
	}

	public static String createCondSql(boolean underscore, Object o, List<Object> ls, int flag, String... keys) {
		StringBuilder where = null;
		PropertyDescriptor[] pda = BeanTools.getPropertyDescriptors(o.getClass());
		if (keys == null) {
			return "";
		}
		where = new StringBuilder();
		where.append(" where ");
		for (PropertyDescriptor pd : pda) {
			String colName = pd.getName();
			if (isReservedWord(pd)) {
				continue;
			}
			colName = underscoreNameIfNecessary(underscore, colName);
			Object val = BeanTools.readProperty(o, pd.getReadMethod());
			appendCond(where, colName, val, ls, keys);
		}
		if (where != null && where.length() > 5) {
			where.delete(where.length() - 4, where.length());
		}
		return where.toString();
	}

	public static String createCondSql(List<String> ls, int flag, String... args) {
		StringBuilder where = null;
		where = new StringBuilder();
		where.append(" where ");
		int i;
		for (i = 0; i < args.length / 2; i++) {
			where.append(args[i * 2]).append("=? and");
			ls.add(args[i * 2 + 1]);
		}
		if (where != null && where.length() > 8) {
			where.delete(where.length() - 4, where.length());
		}
		return where.toString();
	}

	public static boolean checkVal(Object val, int flag) {
		if (flag == 10) {
			flag = 16;
		}
		boolean isNull = val == null;
		boolean isEmpty = isNull || StringUtils.equals(val.toString(), StringUtils.EMPTY);
		boolean isIgNull = (flag & IG_NULL) == IG_NULL;
		if (isIgNull && isEmpty) {
			return true;
		}
		boolean isIgNullNotBlank = (flag & IG_NULLNOTBLANK) == IG_NULLNOTBLANK;
		if (isIgNullNotBlank && isNull) {
			return true;
		}
		boolean igZero = (flag & IG_ZERO) == IG_ZERO;
		if (igZero && !isNull && StringUtils.equals("0", val.toString())) {
			return true;
		}
		boolean igNegative = (flag & IG_NEGATIVE) == IG_NEGATIVE;
		if (igNegative && !isNull && StringUtils.equals("-1", val.toString())) {
			return true;
		}
		boolean igMin = (flag & IG_MIN) == IG_MIN;
		if (igMin && !isNull && val.equals(BeanTools.getMinValue(val.getClass()))) {
			return true;
		}
		return false;
	}

	public void init(Class<? extends Object> clz, String[] k) {
		pdas = BeanTools.getPropertyDescriptors(clz);
		keys = k;
	}

	private static void logSql(String sql, Object... args) {
		LOGGER.debug("Sql : {}, arguments : {}", sql, Arrays.toString(args));
	}

}
