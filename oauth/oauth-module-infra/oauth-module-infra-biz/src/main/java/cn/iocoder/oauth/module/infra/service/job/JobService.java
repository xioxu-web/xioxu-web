package cn.iocoder.oauth.module.infra.service.job;

import cn.iocoder.oauth.framework.common.exception.ServiceException;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.job.JobCreateReqVO;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.job.JobPageReqVO;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.job.JobUpdateReqVO;
import cn.iocoder.oauth.module.infra.dal.dataobject.job.JobDO;
import org.quartz.SchedulerException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * job 定时任务接口
 * @author xiaoxu123
 */
public interface JobService {

    /**
     * 创建定时任务
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createJob(@Valid JobCreateReqVO createReqVO) throws SchedulerException;

    /**
     * 更新定时任务
     *
     * @param updateReqVO 更新信息
     */
    void updateJob(@Valid JobUpdateReqVO updateReqVO) throws SchedulerException;

    /**
     * 更新定时任务的状态
     *
     * @param id 任务编号
     * @param status 状态
     */
    void updateJobStatus(Long id, Integer status) throws SchedulerException;

    /**
     * 触发定时任务
     *
     * @param id 任务编号
     */
    void triggerJob(Long id) throws SchedulerException;

    /**
     * 删除定时任务
     *
     * @param id 编号
     */
    void deleteJob(Long id) throws SchedulerException;

    /**
     * 获得定时任务
     *
     * @param id 编号
     * @return 定时任务
     */
    JobDO getJob(Long id);

    /**
     * 获得定时任务列表
     *
     * @param ids 编号
     * @return 定时任务列表
     */
    List<JobDO> getJobList(Collection<Long> ids);

    /**
     * 获得定时任务分页
     *
     * @param pageReqVO 分页查询
     * @return 定时任务分页
     */
    PageResult<JobDO> getJobPage(JobPageReqVO pageReqVO);


}
