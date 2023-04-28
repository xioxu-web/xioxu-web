package com.spring.middleware.db.router.config;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author xiaoxu123
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfig {

    /**
     * 注入自己重写的数据源
     */
    @Resource
    private DataSource myRoutingDataSource;

    /**
     * 创建SqlSession
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(myRoutingDataSource);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 事务管理器，不写则事务不生效：事务需要知道当前使用的是哪个数据源才能进行事务处理
     */
    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager( myRoutingDataSource );
    }

}
