package cn.iocoder.oauth.module.infra.service.job;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.quartz.core.service.JobLogFrameworkService;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.log.JobLogPageReqVO;
import cn.iocoder.oauth.module.infra.dal.dataobject.job.JobLogDO;

import java.util.Collection;
import java.util.List;

/**
 * Job 日志 Service 接口
 *
 * @author admin
 */
public interface JobLogService extends JobLogFrameworkService {

    /**
     * 获得定时任务
     *
     * @param id 编号
     * @return 定时任务
     */
    JobLogDO getJobLog(Long id);

    /**
     * 获得定时任务列表
     *
     * @param ids 编号
     * @return 定时任务列表
     */
    List<JobLogDO> getJobLogList(Collection<Long> ids);

    /**
     * 获得定时任务分页
     *
     * @param pageReqVO 分页查询
     * @return 定时任务分页
     */
    PageResult<JobLogDO> getJobLogPage(JobLogPageReqVO pageReqVO);

}
