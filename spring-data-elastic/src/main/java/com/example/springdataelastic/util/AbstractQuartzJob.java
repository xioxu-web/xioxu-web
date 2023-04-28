package com.example.springdataelastic.util;

import com.example.springdataelastic.core.ScheduleConstants;
import com.example.springdataelastic.domain.QuartzJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 抽象quartz调用
 * @author xiaoxu123
 */
public abstract class AbstractQuartzJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    /**
     *线程本地变量
     */
    private static ThreadLocal<Date> threadLocal=new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        QuartzJob quartzJob = new QuartzJob();
        BeanUtils.copyBeanProp(quartzJob,context.getMergedJobDataMap().get( ScheduleConstants.TASK_PROPERTIES));

    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param job     系统计划任务
     */
    protected void before(JobExecutionContext context,QuartzJob job){
      threadLocal.set(new Date());
    }

    /**
     *执行后
     * @param context 工作执行上下文对象
     * @param job     系统计划任务
     */
    protected void after(JobExecutionContext context,QuartzJob job){
        threadLocal.set(new Date());
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param job     系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, QuartzJob job) throws Exception;


}
