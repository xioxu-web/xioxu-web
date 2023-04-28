package cn.iocoder.oauth.module.infra.controller.admin.job;

import cn.iocoder.oauth.framework.common.pojo.CommonResult;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.quartz.core.util.CronUtils;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.job.JobCreateReqVO;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.job.JobPageReqVO;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.job.JobRespVO;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.job.JobUpdateReqVO;
import cn.iocoder.oauth.module.infra.convert.job.JobConvert;
import cn.iocoder.oauth.module.infra.dal.dataobject.job.JobDO;
import cn.iocoder.oauth.module.infra.service.job.JobService;
import io.swagger.annotations.*;
import org.quartz.SchedulerException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static cn.iocoder.oauth.framework.common.pojo.CommonResult.success;

/**
 * @author xiaoxu123
 */
@Api(tags ="后台管理 -定时任务")
@RestController("/")
@RequestMapping("/infra/job")
@Valid
public class JobController {

    @Resource
    private JobService jobService;

    /**
     * 创建定时任务
     */
    @PostMapping("/create")
    @ApiModelProperty("创建定时任务")
    @PreAuthorize("@ss.hasPermission('infra:job:create')")
    public CommonResult<Long> createJob(@Valid @RequestBody JobCreateReqVO  createReqVO) throws SchedulerException {
      return success(jobService.createJob(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新定时任务")
    @PreAuthorize("@ss.hasPermission('infra:job:update')")
    public CommonResult<Boolean> updateJob(@Valid @RequestBody JobUpdateReqVO updateReqVO)
            throws SchedulerException {
        jobService.updateJob(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("更新定时任务的状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "status", value = "状态", required = true, example = "1", dataTypeClass = Integer.class),
    })
    @PreAuthorize("@ss.hasPermission('infra:job:update')")
    public CommonResult<Boolean> updateJobStatus(@RequestParam(value = "id") Long id, @RequestParam("status") Integer status)
            throws SchedulerException {
        jobService.updateJobStatus(id, status);
        return success(true);
    }

    @PutMapping("/trigger")
    @ApiOperation("触发定时任务")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('infra:job:trigger')")
    public CommonResult<Boolean> triggerJob(@RequestParam("id") Long id) throws SchedulerException {
        jobService.triggerJob(id);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除定时任务")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('infra:job:delete')")
    public CommonResult<Boolean> deleteJob(@RequestParam("id") Long id)
            throws SchedulerException {
        jobService.deleteJob(id);
        return success(true);
    }

    /**
     * 获取定时任务
     */
    @GetMapping("/get")
    @ApiModelProperty("获取定时任务")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('infra:job:query')")
    public CommonResult<JobRespVO> getJob(@RequestParam("id") Long id){
        JobDO job = jobService.getJob( id );
        return success(JobConvert.INSTANCE.convert(job));
    }

    @GetMapping("/list")
    @ApiModelProperty("获取定时任务列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('infra:job:query')")
    public CommonResult<List<JobRespVO>> getJobList(@RequestParam("ids") Collection<Long> ids){
        List<JobDO> jobList = jobService.getJobList(ids);
        return success(JobConvert.INSTANCE.convertList(jobList));
    }

    @GetMapping("/page")
    @ApiOperation("获得定时任务分页")
    @PreAuthorize("@ss.hasPermission('infra:job:query')")
    public CommonResult<PageResult<JobRespVO>> getJobPage(@Valid JobPageReqVO pageVO) {
        PageResult<JobDO> pageResult = jobService.getJobPage(pageVO);
        return success(JobConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/get_next_times")
    @ApiOperation("获得定时任务的下 n 次执行时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "count", value = "数量", example = "5", dataTypeClass = Long.class)
    })
    @PreAuthorize("@ss.hasPermission('infra:job:query')")
    public CommonResult<List<Date>> getJobNextTimes(@RequestParam("id") Long id,
                                                    @RequestParam(value = "count", required = false, defaultValue = "5") Integer count) {
        JobDO job = jobService.getJob(id);
        if (job == null) {
            return success( Collections.emptyList());
        }
        return success(CronUtils.getNextTimes(job.getCronExpression(), count));
    }

}
