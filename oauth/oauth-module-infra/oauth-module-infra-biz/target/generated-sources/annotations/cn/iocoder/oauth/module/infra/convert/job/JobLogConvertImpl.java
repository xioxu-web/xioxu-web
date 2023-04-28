package cn.iocoder.oauth.module.infra.convert.job;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.log.JobLogRespVO;
import cn.iocoder.oauth.module.infra.dal.dataobject.job.JobLogDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-25T14:54:50+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class JobLogConvertImpl implements JobLogConvert {

    @Override
    public JobLogRespVO convert(JobLogDO bean) {
        if ( bean == null ) {
            return null;
        }

        JobLogRespVO jobLogRespVO = new JobLogRespVO();

        jobLogRespVO.setJobId( bean.getJobId() );
        jobLogRespVO.setHandlerName( bean.getHandlerName() );
        jobLogRespVO.setHandlerParam( bean.getHandlerParam() );
        jobLogRespVO.setExecuteIndex( bean.getExecuteIndex() );
        jobLogRespVO.setBeginTime( bean.getBeginTime() );
        jobLogRespVO.setEndTime( bean.getEndTime() );
        jobLogRespVO.setDuration( bean.getDuration() );
        jobLogRespVO.setStatus( bean.getStatus() );
        jobLogRespVO.setResult( bean.getResult() );
        jobLogRespVO.setId( bean.getId() );
        jobLogRespVO.setCreateTime( bean.getCreateTime() );

        return jobLogRespVO;
    }

    @Override
    public List<JobLogRespVO> convertList(List<JobLogDO> list) {
        if ( list == null ) {
            return null;
        }

        List<JobLogRespVO> list1 = new ArrayList<JobLogRespVO>( list.size() );
        for ( JobLogDO jobLogDO : list ) {
            list1.add( convert( jobLogDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<JobLogRespVO> convertPage(PageResult<JobLogDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<JobLogRespVO> pageResult = new PageResult<JobLogRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );

        return pageResult;
    }
}
