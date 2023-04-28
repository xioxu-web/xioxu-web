package com.example.springdataelastic.controller;

import com.example.springdataelastic.core.BaseController;
import com.example.springdataelastic.core.Constants;
import com.example.springdataelastic.domain.QuartzJob;
import com.example.springdataelastic.service.IQuartzJobService;
import com.example.springdataelastic.util.ApiResp;
import com.example.springdataelastic.util.CronUtils;
import com.example.springdataelastic.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaoxu123
 */
@RestController
@RequestMapping("/quartz/job")
public class QuartzJobController extends BaseController {

    private Logger logger= LoggerFactory.getLogger(QuartzJobController.class);

    @Autowired
     private IQuartzJobService quartzJobService;

    /**
     * 新增任务
     */
    @ApiModelProperty(value = "添加task任务",notes ="把任务信息添加到数据库")
    @PostMapping("/add")
    public ApiResp<QuartzJob> add(@RequestBody QuartzJob job) throws Exception {
      if(!CronUtils.isValid(job.getCronExpression())){
          return ApiResp.<QuartzJob> builder().code(error).msg("新增任务'" + job.getJobName() +"'失败，Cron表达式不正确").build();
      }else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_RMI)){
          return ApiResp.<QuartzJob> builder().code(error).msg("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'rmi://'调用").build();
      }else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(),Constants.LOOKUP_LDAP)){
          return ApiResp.<QuartzJob> builder().code(error).msg("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'ldap://'调用").build();
      }
        quartzJobService.insertJob(job);
        return ApiResp.<QuartzJob>builder().code(success).build();
    }

    @ApiOperation(value = "根据任务ID修改状态，0启动 1暂停")
    @PostMapping(value = "changeStatus")
    public ApiResp<Object> changeStatus(Long jobId, String status) throws SchedulerException {
        quartzJobService.changeStatus(jobId, status);
        return ApiResp.builder().code(success).build();
    }

    @ApiOperation(value = "修改task任务", notes = "根据ID修改已有的任务，同时更新调度任务信息")
    @PostMapping(value = "update")
    public ApiResp<QuartzJob> update(@RequestBody QuartzJob job) throws Exception {
        if (!CronUtils.isValid(job.getCronExpression())) {
            return ApiResp.<QuartzJob>builder().code(error).msg("修改任务'" + job.getJobName() + "'失败，Cron表达式不正确").build();
        } else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_RMI)) {
            return ApiResp.<QuartzJob>builder().code(error).msg("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'rmi://'调用").build();
        } else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_LDAP)) {
            return ApiResp.<QuartzJob>builder().code(error).msg("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'ldap://'调用").build();
        }
        quartzJobService.updateJob(job);
        return ApiResp.<QuartzJob>builder().code(success).build();
    }

}
