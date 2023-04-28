package com.example.springdataelastic.service.Impl;

import com.example.springdataelastic.core.Constants;
import com.example.springdataelastic.core.ScheduleConstants;
import com.example.springdataelastic.domain.QuartzJob;
import com.example.springdataelastic.mapper.QuartzMapper;
import com.example.springdataelastic.service.IQuartzJobService;
import com.example.springdataelastic.util.ScheduleUtils;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author xiaoxu123
 */
@Service
public class IQuartzJobServiceImpl implements IQuartzJobService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private QuartzMapper quartzMapper;

    /**
     * 查询所有定时任务列表
     */
    @PostConstruct
    public void init() throws Exception {
        scheduler.clear();
        List<QuartzJob> quartzJobs = quartzMapper.selectJobAll();
        for (QuartzJob job:quartzJobs) {
            ScheduleUtils.createScheduleJob(scheduler,job);
        }
    }

    /**
     *新增任务
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public int insertJob(QuartzJob job) throws Exception {
        // 先将任务设置为暂停状态
     job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int row = quartzMapper.insert(job);
        if(row>0){
          ScheduleUtils.createScheduleJob(scheduler,job);
        }
        return row;
    }

    /**
     * 暂停任务
     */
    @Override
    public int pauseJob(QuartzJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int taskstatus = quartzMapper.updateById(job);
        if (taskstatus > 0) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return taskstatus;
    }

    /**
     * 重启定时任务
     */
    @Override
    public int resumeJob(QuartzJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        int taskstatus= quartzMapper.updateById( job );
        if(taskstatus>0){
          scheduler.resumeJob( ScheduleUtils.getJobKey(jobId,jobGroup));
        }
        return taskstatus;
    }

    @Override
    public int deleteJob(QuartzJob job) throws SchedulerException {
        return 0;
    }

    /**
     * 改变定时任务状态
     */
    @Override
    public int changeStatus(Long jobId, String status) throws SchedulerException {
        int taskstatus= quartzMapper.changeStatus( jobId,status);
        if(taskstatus==0){
            return taskstatus;
        }
        //更新成功，需要改调度器内任务的状态
        QuartzJob job = quartzMapper.selectJobById( jobId );
        //根据状态来启动或者关闭
        if(ScheduleConstants.Status.NORMAL.getValue().equals(status)){
           resumeJob(job);
        }

        return taskstatus;
    }

    @Override
    public void run(QuartzJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        QuartzJob properties = quartzMapper.selectJobById(job.getJobId());
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, properties);
        scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, jobGroup), dataMap);
    }


    @Override
    public int updateJob(QuartzJob job) throws SchedulerException, Exception {
        return 0;
    }

    @Override
    public boolean checkCronExpressionIsValid(String cronExpression) {
        return false;
    }
}
