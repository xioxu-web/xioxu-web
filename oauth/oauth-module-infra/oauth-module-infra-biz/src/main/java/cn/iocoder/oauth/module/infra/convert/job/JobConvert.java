package cn.iocoder.oauth.module.infra.convert.job;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.job.JobCreateReqVO;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.job.JobRespVO;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.job.JobUpdateReqVO;
import cn.iocoder.oauth.module.infra.dal.dataobject.job.JobDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 定时任务 Convert
 *
 * @author admin
 */
@Mapper
public interface JobConvert {

    JobConvert INSTANCE = Mappers.getMapper(JobConvert.class);

    JobDO convert(JobCreateReqVO bean);

    JobDO convert(JobUpdateReqVO bean);

    JobRespVO convert(JobDO bean);

    List<JobRespVO> convertList(List<JobDO> list);

    PageResult<JobRespVO> convertPage(PageResult<JobDO> page);


}
