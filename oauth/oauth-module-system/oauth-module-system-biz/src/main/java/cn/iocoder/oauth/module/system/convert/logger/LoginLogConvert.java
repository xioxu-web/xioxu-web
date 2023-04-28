package cn.iocoder.oauth.module.system.convert.logger;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.iocoder.oauth.module.system.controller.admin.logger.vo.loginlog.LoginLogRespVO;
import cn.iocoder.oauth.module.system.dal.dataobject.logger.LoginLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface LoginLogConvert {

    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

    PageResult<LoginLogRespVO> convertPage(PageResult<LoginLogDO> page);


    LoginLogDO convert(LoginLogCreateReqDTO bean);

}
