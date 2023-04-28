package cn.iocoder.oauth.module.infra.convert.logger;

import cn.iocoder.oauth.framework.apilog.core.service.dto.ApiErrorLogCreateReqDTO;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogRespVO;
import cn.iocoder.oauth.module.infra.dal.dataobject.logger.ApiErrorLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * API 错误日志 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface ApiErrorLogConvert {

    ApiErrorLogConvert INSTANCE = Mappers.getMapper(ApiErrorLogConvert.class);

    ApiErrorLogRespVO convert(ApiErrorLogDO bean);

    PageResult<ApiErrorLogRespVO> convertPage(PageResult<ApiErrorLogDO> page);

    ApiErrorLogDO convert(ApiErrorLogCreateReqDTO bean);

}
