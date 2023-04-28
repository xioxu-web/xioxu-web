package com.example.springdataelastic.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.spi.JobFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xiaoxu123
 */
@Component
public class TestTask implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("这是一个测试任务:"+LocalDateTime.now().format(DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" )));
    }

}
