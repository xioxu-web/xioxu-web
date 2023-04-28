package com.example.springdataelastic.init;

import com.example.springdataelastic.SpringDataElasticApplication;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@SpringBootTest(classes = SpringDataElasticApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class QuartzTest implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println( LocalDateTime.now().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" ) ) );
    }

    public static void main(String[] args) throws SchedulerException {
        //创建一个Scheduler
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        //启动Scheduler
        scheduler.start();
        // 新建一个Job, 指定执行类是QuartzTest, 指定一个K/V类型的数据, 指定job的name和group
        JobDetail jobDetail = JobBuilder.newJob( QuartzTest.class )
                .usingJobData( "jobData", "test" )
                .withIdentity( "myJob", "myJobGroup" )
                .build();
        // 新建一个Trigger,表示JobDetail的调度计划, 这里的cron表达式是 每1秒执行一次
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity( "myTrigger", "myTriggerGroup" )
                .startNow()
                .withSchedule( CronScheduleBuilder.cronSchedule( "0/5 * * * * ?" ) )
                .build();
        scheduler.scheduleJob(jobDetail,trigger);
    }
}
