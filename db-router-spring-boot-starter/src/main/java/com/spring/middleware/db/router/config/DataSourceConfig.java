package com.spring.middleware.db.router.config;

import com.spring.middleware.db.router.Enum.DBTypeEnum;
import com.spring.middleware.db.router.dynamic.DynamicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoxu123
 */
@Configuration
public class DataSourceConfig {
    /**
     * 注入主数据源
     */
   @Bean
   @ConfigurationProperties(prefix ="spring.datasource.master")
   public DataSource masterDataSource(){
    return DataSourceBuilder.create().build();
   }

    /**
     * 注入从库数据源
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {

        return DataSourceBuilder.create().build();
    }

    /**
     * 配置选择数据源
     * @param masterDataSource
     * @param slaveDataSource
     * @return DataSource
     */
    @Bean
    public DataSource myRoutingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource, @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DBTypeEnum.MASTER, masterDataSource);
        targetDataSource.put(DBTypeEnum.SLAVE, slaveDataSource);

        DynamicDataSource myRoutingDataSource = new DynamicDataSource();
        //找不到用默认数据源
        myRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
        //可选择目标数据源
        myRoutingDataSource.setTargetDataSources(targetDataSource);

        return myRoutingDataSource;
    }

}
