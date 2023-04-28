package cn.iocoder.oauth.module.infra.convert.job;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.log.JobLogRespVO;
import cn.iocoder.oauth.module.infra.dal.dataobject.job.JobLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 定时任务日志 Convert
 *
 * @author admin
 */
@Mapper
public interface JobLogConvert {

    JobLogConvert INSTANCE = Mappers.getMapper(JobLogConvert.class);

    JobLogRespVO convert(JobLogDO bean);

    List<JobLogRespVO> convertList(List<JobLogDO> list);

    PageResult<JobLogRespVO> convertPage(PageResult<JobLogDO> page);


}
