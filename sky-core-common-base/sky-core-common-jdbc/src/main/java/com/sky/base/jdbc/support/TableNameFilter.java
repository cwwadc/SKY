package com.sky.base.jdbc.support;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;

import com.sky.base.jdbc.spring.BaseJdbcAccessor;

import java.util.Collection;
import java.util.List;

/**
 * @author lizp
 * @version 1.0.0
 * @Title
 * @Description
 * @date 2019/4/24
 */
public class TableNameFilter extends BaseJdbcAccessor {

    public Collection<String> filterNotExistTable(Collection<String> tableNames) {
        Assert.notEmpty(tableNames, "Argument[tableNames] must not be empty");
        StringBuilder sql = new StringBuilder("select table_name from user_tables where table_name in (");
        tableNames.stream().forEach(each -> sql.append("?,"));
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        List<String> existingTableNames = queryForList(sql.toString(), String.class, tableNames.toArray(new Object[tableNames.size()]));
        logger.info("Filter not exist table, Original list of names is {}, filter result is {}", tableNames, existingTableNames);
        return CollectionUtils.isEmpty(existingTableNames) ? CollectionUtils.emptyCollection() : existingTableNames;
    }

}
