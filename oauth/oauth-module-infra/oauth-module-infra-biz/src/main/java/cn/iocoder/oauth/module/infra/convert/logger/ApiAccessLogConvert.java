package cn.iocoder.oauth.module.infra.convert.logger;

import cn.iocoder.oauth.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogRespVO;
import cn.iocoder.oauth.module.infra.dal.dataobject.logger.ApiAccessLogDO;
import cn.iocoder.oauth.framework.apilog.core.service.dto.ApiAccessLogCreateReqDTO;
import cn.iocoder.oauth.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * API 访问日志 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface ApiAccessLogConvert {

    ApiAccessLogConvert INSTANCE = Mappers.getMapper(ApiAccessLogConvert.class);

    ApiAccessLogRespVO convert(ApiAccessLogDO bean);

    List<ApiAccessLogRespVO> convertList(List<ApiAccessLogDO> list);

    PageResult<ApiAccessLogRespVO> convertPage(PageResult<ApiAccessLogDO> page);


    ApiAccessLogDO convert(ApiAccessLogCreateReqDTO bean);

}
