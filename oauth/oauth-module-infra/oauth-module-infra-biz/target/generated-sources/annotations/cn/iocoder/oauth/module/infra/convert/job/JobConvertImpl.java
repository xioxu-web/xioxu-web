package cn.iocoder.oauth.module.infra.convert.job;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.job.JobCreateReqVO;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.job.JobRespVO;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.job.JobUpdateReqVO;
import cn.iocoder.oauth.module.infra.dal.dataobject.job.JobDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-25T14:54:50+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class JobConvertImpl implements JobConvert {

    @Override
    public JobDO convert(JobCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        JobDO jobDO = new JobDO();

        jobDO.setName( bean.getName() );
        jobDO.setHandlerName( bean.getHandlerName() );
        jobDO.setHandlerParam( bean.getHandlerParam() );
        jobDO.setCronExpression( bean.getCronExpression() );
        jobDO.setRetryCount( bean.getRetryCount() );
        jobDO.setRetryInterval( bean.getRetryInterval() );
        jobDO.setMonitorTimeout( bean.getMonitorTimeout() );

        return jobDO;
    }

    @Override
    public JobDO convert(JobUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        JobDO jobDO = new JobDO();

        jobDO.setId( bean.getId() );
        jobDO.setName( bean.getName() );
        jobDO.setHandlerParam( bean.getHandlerParam() );
        jobDO.setCronExpression( bean.getCronExpression() );
        jobDO.setRetryCount( bean.getRetryCount() );
        jobDO.setRetryInterval( bean.getRetryInterval() );
        jobDO.setMonitorTimeout( bean.getMonitorTimeout() );

        return jobDO;
    }

    @Override
    public JobRespVO convert(JobDO bean) {
        if ( bean == null ) {
            return null;
        }

        JobRespVO jobRespVO = new JobRespVO();

        jobRespVO.setName( bean.getName() );
        jobRespVO.setHandlerParam( bean.getHandlerParam() );
        jobRespVO.setCronExpression( bean.getCronExpression() );
        jobRespVO.setRetryCount( bean.getRetryCount() );
        jobRespVO.setRetryInterval( bean.getRetryInterval() );
        jobRespVO.setMonitorTimeout( bean.getMonitorTimeout() );
        jobRespVO.setId( bean.getId() );
        jobRespVO.setStatus( bean.getStatus() );
        jobRespVO.setHandlerName( bean.getHandlerName() );
        jobRespVO.setCreateTime( bean.getCreateTime() );

        return jobRespVO;
    }

    @Override
    public List<JobRespVO> convertList(List<JobDO> list) {
        if ( list == null ) {
            return null;
        }

        List<JobRespVO> list1 = new ArrayList<JobRespVO>( list.size() );
        for ( JobDO jobDO : list ) {
            list1.add( convert( jobDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<JobRespVO> convertPage(PageResult<JobDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<JobRespVO> pageResult = new PageResult<JobRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );

        return pageResult;
    }
}
