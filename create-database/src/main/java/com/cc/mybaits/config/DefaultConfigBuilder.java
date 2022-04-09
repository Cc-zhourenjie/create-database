package com.cc.mybaits.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

/**
 * 默认方式加载Configuration
 */
public class DefaultConfigBuilder {

    private Configuration configuration;

    private static String DRIVER = "com.mysql.jdbc.Driver";
    private static String URL = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC";
    private static String USERNAME = "root";
    private static String PASSWORD = "123456";


    public DefaultConfigBuilder() {
        this.configuration = new Configuration();
    }

    public Configuration getConfiguration() {
        return configuration;
    }


    public Configuration parseConfiguration() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(URL);
        druidDataSource.setUsername(USERNAME);
        druidDataSource.setPassword(PASSWORD);
        druidDataSource.setDriverClassName(DRIVER);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment development = new Environment("development", transactionFactory, druidDataSource);
        configuration.setEnvironment(development);
        return configuration;
    }


}
