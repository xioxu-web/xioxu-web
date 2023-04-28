package cn.iocoder.oauth.framework.quartz.config;

import cn.iocoder.oauth.framework.quartz.core.scheduler.SchedulerManager;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author xiaoxu123
 */
@Configuration
@EnableScheduling //开启spring自带的定时任务
public class OauthQuartzAutoConfiguration {

    @Bean
    public SchedulerManager schedulerManager(Scheduler scheduler){
        return new SchedulerManager(scheduler);
    }
}
