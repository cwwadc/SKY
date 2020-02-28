package com.sky.core.service.api.configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.sky.base.jdbc.spring.CoreDao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
  * 数据库配置bean 
 *
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class ApiDataSourceConfiguration {

    @Value("${sky.api.datasource.name}")
    private String name;
    @Value("${sky.api.datasource.driverClassName}")
    private String driverClassName;
    @Value("${sky.api.datasource.url}")
    private String url;
    @Value("${sky.api.datasource.username}")
    private String username;
    @Value("${sky.api.datasource.password}")
    private String password;
    @Value("${sky.api.datasource.initialSize}")
    private int initialSize;
    @Value("${sky.api.datasource.minIdle}")
    private int minIdle;
    @Value("${sky.api.datasource.maxActive}")
    private int maxActive;
    @Value("${sky.api.datasource.maxWait}")
    private long maxWait;
    @Value("${sky.api.datasource.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;
    @Value("${sky.api.datasource.minEvictableIdleTimeMillis}")
    private long minEvictableIdleTimeMillis;
    @Value("${sky.api.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${sky.api.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${sky.api.datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${sky.api.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${sky.api.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${sky.api.datasource.validationQuery}")
    private String validationQuery;
    @Value("${sky.api.datasource.removeAbandoned}")
    private boolean removeAbandoned;
    @Value("${sky.api.datasource.removeAbandonedTimeout}")
    private int removeAbandonedTimeout;
    @Value("${sky.api.datasource.logAbandoned}")
    private boolean logAbandoned;

    @Bean
    public DataSource skyDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setName(name);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setLogAbandoned(logAbandoned);
        dataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        dataSource.setRemoveAbandoned(removeAbandoned);
//        dataSource.setFilters("stat");

        List<Filter> proxyFilters = new ArrayList<>(1);
        proxyFilters.add(logFilter());
        proxyFilters.add(statFilter());
        dataSource.setProxyFilters(proxyFilters);
        return dataSource;
    }

    @Bean("logFilter")
    public Filter logFilter() {
        Slf4jLogFilter logFilter = new Slf4jLogFilter();
        logFilter.setStatementExecutableSqlLogEnable(false);
        logFilter.setResultSetLogEnabled(false);
        return logFilter;
    }

    @Bean("statFilter")
    public Filter statFilter() {
        StatFilter statFilter = new StatFilter();
        statFilter.setMergeSql(true);
        statFilter.setSlowSqlMillis(10000L);
        statFilter.setLogSlowSql(true);
        return statFilter;
    }

    @Bean("transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager() throws SQLException {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(skyDataSource());
        return manager;
    }

    @Bean
    public JdbcTemplate skyApiJdbcTemplate() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(skyDataSource());
        return jdbcTemplate;
    }

    @Bean
    public CoreDao skyApiCoreDao() throws SQLException {
        CoreDao dao = new CoreDao();
        dao.setJdbcTemplate(skyApiJdbcTemplate());
        return dao;
    }
}
