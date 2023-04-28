package cn.iocoder.oauth.module.infra.dal.mysql.job;

import cn.iocoder.oauth.framework.common.pojo.PageParam;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.oauth.framework.mybatis.core.query.QueryWrapperX;
import cn.iocoder.oauth.module.infra.controller.admin.job.vo.log.JobLogPageReqVO;
import cn.iocoder.oauth.module.infra.dal.dataobject.job.JobLogDO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xiaoxu123
 */
@Mapper
public interface JobLogMapper extends BaseMapperX<JobLogDO> {

    default PageResult<JobLogDO> selectPage(JobLogPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<JobLogDO>()
                .eqIfPresent("job_id", reqVO.getJobId())
                .likeIfPresent("handler_name", reqVO.getHandlerName())
                .geIfPresent("begin_time", reqVO.getBeginTime())
                .leIfPresent("end_time", reqVO.getEndTime())
                .eqIfPresent("status", reqVO.getStatus())
                .orderByDesc("id")
        );
    }

}
