package com.wmc.config.ebean;

import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.spring.txn.SpringJdbcTransactionManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Ebean Spring 支持
 *
 * @author YeFei
 */
@Component
public class EbeanConfigFactoryBean implements FactoryBean<EbeanServer>, InitializingBean {

    @Autowired
    private EbeanConfig config;

    @Autowired
    private DataSource dataSource;

    private EbeanServer server;

    @Override
    public EbeanServer getObject() throws Exception {
        return server;
    }

    @Override
    public Class<?> getObjectType() {
        return EbeanServer.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 如果没有指定当前用户提供方法则默认返回 null
        if (config.getCurrentUserProvider() == null) {
            config.setCurrentUserProvider(() -> null);
        }
        // config.setCurrentUserProvider(currentUser);
        // set the spring's datasource and transaction manager.
        config.setDataSource(dataSource);
        config.setExternalTransactionManager(new SpringJdbcTransactionManager());
        /*
         * ebean运行在sql server的困难就是：
         * 1、生成的脚本会执行一堆不知道什么，sql server上会报错，可以设置config.setDdlExtra()使其不执行
         * 2、不支持smallint(1)类型
         *
         * config.setDdlInitSql()：Set a SQL script to execute before the "create all" DDL has been run.
         * config.setDdlSeedSql()：Set a SQL script to execute after the "create all" DDL has been run.
         */
        server = EbeanServerFactory.create(config);
    }
}
