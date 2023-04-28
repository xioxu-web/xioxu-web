package cn.iocoder.oauth.module.infra.dal.mysql.logger;
import cn.iocoder.oauth.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogExportReqVO;
import cn.iocoder.oauth.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogPageReqVO;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.oauth.framework.mybatis.core.query.QueryWrapperX;
import cn.iocoder.oauth.module.infra.dal.dataobject.logger.ApiAccessLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * API 访问日志 Mapper
 *
 * @author xiaoxu
 */
@Mapper
public interface ApiAccessLogMapper extends BaseMapperX<ApiAccessLogDO>  {

    default PageResult<ApiAccessLogDO> selectPage(ApiAccessLogPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<ApiAccessLogDO>()
                .eqIfPresent("user_id", reqVO.getUserId())
                .eqIfPresent("user_type", reqVO.getUserType())
                .eqIfPresent("application_name", reqVO.getApplicationName())
                .likeIfPresent("request_url", reqVO.getRequestUrl())
                .betweenIfPresent("begin_time", reqVO.getBeginBeginTime(), reqVO.getEndBeginTime())
                .geIfPresent("duration", reqVO.getDuration())
                .eqIfPresent("result_code", reqVO.getResultCode())
                .orderByDesc("id")
        );
    }


    default List<ApiAccessLogDO> selectList(ApiAccessLogExportReqVO reqVO) {
        return selectList(new QueryWrapperX<ApiAccessLogDO>()
                .eqIfPresent("user_id", reqVO.getUserId())
                .eqIfPresent("user_type", reqVO.getUserType())
                .eqIfPresent("application_name", reqVO.getApplicationName())
                .likeIfPresent("request_url", reqVO.getRequestUrl())
                .betweenIfPresent("begin_time", reqVO.getBeginBeginTime(), reqVO.getEndBeginTime())
                .geIfPresent("duration", reqVO.getDuration())
                .eqIfPresent("result_code", reqVO.getResultCode())
                .orderByDesc("id")
        );
    }

}
