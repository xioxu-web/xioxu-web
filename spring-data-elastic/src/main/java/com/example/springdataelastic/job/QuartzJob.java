package com.example.springdataelastic.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author xiaoxu123
 * 创建QuartzJob定时任务类
 */
// 持久化
@PersistJobDataAfterExecution
// 禁止并发执行
@DisallowConcurrentExecution
public class QuartzJob extends QuartzJobBean {
     private static final Logger log= LoggerFactory.getLogger(QuartzJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String taskName = context.getJobDetail().getJobDataMap().getString("name");
        log.info("---> Quartz job, time:{"+new Date()+"} ,name:{"+taskName+"}<----");
    }


}
