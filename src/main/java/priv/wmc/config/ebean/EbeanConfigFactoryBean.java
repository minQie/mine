package priv.wmc.config.ebean;

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
    public EbeanServer getObject() {
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
    public void afterPropertiesSet() {
        // 如果没有指定当前用户提供方法则默认返回 null（主要是为了支持@WhoCreated注解）
        if (config.getCurrentUserProvider() == null) {
            config.setCurrentUserProvider(() -> null);
        }

        // set the spring's datasource and transaction manager.
        config.setDataSource(dataSource);
        config.setExternalTransactionManager(new SpringJdbcTransactionManager());

        /*
         * ebean运行在sql server的困难就是：
         * 1、生成的脚本会执行一堆不知道什么，sql server上会报错，可以设置config.setDdlExtra()使其不执行
         * 2、不支持smallint(1)类型
         * 3、字符类型，默认是“varchar”（定义的长度和能存储的中文数量不符），应该采用“nvarchar”
         *
         * config.setDdlInitSql()：Set a SQL script to execute before the "create all" DDL has been run.
         * config.setDdlSeedSql()：Set a SQL script to execute after the "create all" DDL has been run.
         */
        server = EbeanServerFactory.create(config);
    }
}
