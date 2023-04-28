package com.example.springdataelastic.util;

import com.example.springdataelastic.domain.QuartzJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（允许并发执行）
 * @author xiaoxu123
 */
public class QuartzJobExecution extends AbstractQuartzJob{

    @Override
    protected void doExecute(JobExecutionContext context, QuartzJob job) throws Exception {
      JobInvokeUtil.invokeMethod(job);
    }
}
