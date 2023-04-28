package com.example.springdataelastic.config.quartz;
import com.example.springdataelastic.config.app.AppConfig;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;


/**
 * @author xiaoxu123
 */
@Configuration
public class QuartzConfiguration {

    @Autowired
    private AppConfig appConfig;

    @Bean(name ="scheduler")
    public Scheduler scheduler() throws SchedulerException {
        StdSchedulerFactory factory = new StdSchedulerFactory(quartzProperties());
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        return scheduler;
    }

    @Bean
    public Properties quartzProperties(){
        Properties prop= new Properties();
        prop.put("quartz.scheduler.instanceName", appConfig.getQuartzSchedulerInstanceName());
        prop.put("org.quartz.scheduler.instanceId", appConfig.getQuartzSchedulerInstanceId());
        prop.put("org.quartz.scheduler.skipUpdateCheck", appConfig.getQuartzSchedulerSkipUpdateCheck());
        prop.put("org.quartz.scheduler.jobFactory.class", appConfig.getQuartzSchedulerJobFactoryClass());
        prop.put("org.quartz.jobStore.class", appConfig.getQuartzJobStoreClass());
        prop.put("org.quartz.jobStore.driverDelegateClass", appConfig.getQuartzJobStoreDriverDelegateClass());
        prop.put("org.quartz.jobStore.dataSource", appConfig.getQuartzJobStoreDatasource());
        prop.put("org.quartz.jobStore.tablePrefix", appConfig.getQuartzJobStoreTablePrefix());
        prop.put("org.quartz.jobStore.isClustered", appConfig.getQuartzJobStoreIsClustered());
        prop.put("org.quartz.threadPool.class", appConfig.getQuartzThreadPoolClass());
        prop.put("org.quartz.threadPool.threadCount", appConfig.getQuartzThreadPoolThreadCount());
        prop.put("org.quartz.dataSource.quartzDataSource.connectionProvider.class", appConfig.getQuartzDatasourceQuartzDataSourceConnectionProviderClass());
        prop.put("org.quartz.dataSource.quartzDataSource.driver", appConfig.getQuartzDatasourceQuartzDataSourceDriver());
        prop.put("org.quartz.dataSource.quartzDataSource.URL", appConfig.getQuartzDatasourceQuartzDataSourceUrl());
        prop.put("org.quartz.dataSource.quartzDataSource.username", appConfig.getQuartzDatasourceQuartzDataSourceUserName());
        prop.put("org.quartz.dataSource.quartzDataSource.password", appConfig.getQuartzDatasourceQuartzDataSourcePassword());
        prop.put("org.quartz.dataSource.quartzDataSource.maxConnections", appConfig.getQuartzDatasourceQuartzDataSourceMaxConnections());
        return prop;
    }




}
