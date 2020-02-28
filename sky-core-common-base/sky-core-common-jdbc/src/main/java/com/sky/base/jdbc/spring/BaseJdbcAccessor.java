package com.sky.base.jdbc.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @author lizp
 * @version 1.0.0
 * @Title 基础jdbc访问器
 * @Description
 * @date 2019-03-20
 */
public class BaseJdbcAccessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected JdbcTemplate jdbcTemplate;

    public int[] batchUpdate(String sql, List<Object[]> batchArgs) {
        logSql(sql, batchArgs);
        return jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    public int update(String sql, Object... args) {
        logSql(sql, args);
        return jdbcTemplate.update(sql, args);
    }

    public int delete(String sql, Object... args) {
        return update(sql, args);
    }

    public <T> List<T> queryForList(String sql, Class<T> elementType, Object... args) {
        logSql(sql, args);
        return jdbcTemplate.queryForList(sql, elementType, args);
    }

    private void logSql(String sql, Object... args) {
        logger.debug("Sql : {}, arguments : {}", sql, Arrays.toString(args));
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
