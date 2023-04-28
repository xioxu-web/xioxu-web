package cn.iocoder.oauth.module.infra.service.job.Impl;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.log.JobLogPageReqVO;
import cn.iocoder.oauth.module.infra.dal.dataobject.job.JobDO;
import cn.iocoder.oauth.module.infra.dal.dataobject.job.JobLogDO;
import cn.iocoder.oauth.module.infra.dal.mysql.job.JobLogMapper;
import cn.iocoder.oauth.module.infra.enums.job.JobLogStatusEnum;
import cn.iocoder.oauth.module.infra.service.job.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.List;
/**
 * @author xiaoxu123
 */
@Service
@Slf4j
public class JobLogServiceImpl implements JobLogService {

   @Resource
   private JobLogMapper jobLogMapper;

    @Override
    public Long createJobLog(@NotNull(message = "任务编号不能为空") Long jobId, @NotNull(message = "开始时间") Date beginTime, @NotEmpty(message = "Job 处理器的名字不能为空") String jobHandlerName, String jobHandlerParam, @NotNull(message = "第几次执行不能为空") Integer executeIndex) {
        JobLogDO log = JobLogDO.builder().jobId(jobId).handlerName(jobHandlerName).handlerParam(jobHandlerParam).executeIndex(executeIndex)
                .beginTime(beginTime).status( JobLogStatusEnum.RUNNING.getStatus()).build();
        jobLogMapper.insert(log);
        return log.getJobId();
    }

    @Override
    public void updateJobLogResultAsync(@NotNull(message = "日志编号不能为空") Long logId, @NotNull(message = "结束时间不能为空") Date endTime, @NotNull(message = "运行时长不能为空") Integer duration, boolean success, String result) {
         try {
             JobLogDO updateObj = JobLogDO.builder().id( logId ).endTime( endTime ).duration( duration )
                     .status( success ? JobLogStatusEnum.SUCCESS.getStatus() : JobLogStatusEnum.FAILURE.getStatus() ).result( result ).build();
             jobLogMapper.updateById( updateObj );
         }catch (Exception e) {
             log.error( "[updateJobLogResultAsync][logId({}) endTime({}) duration({}) success({}) result({})]",
                     logId, endTime, duration, success, result );
         }
    }

    @Override
    public JobLogDO getJobLog(Long id) {
        return jobLogMapper.selectById(id);
    }

    @Override
    public List<JobLogDO> getJobLogList(Collection<Long> ids) {
        return jobLogMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<JobLogDO> getJobLogPage(JobLogPageReqVO pageReqVO) {
        return jobLogMapper.selectPage(pageReqVO);
    }
}
