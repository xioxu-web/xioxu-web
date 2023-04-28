package cn.iocoder.oauth.module.system.service.logger;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.iocoder.oauth.module.system.controller.admin.logger.vo.loginlog.LoginLogPageReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.logger.LoginLogDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 登录日志 Service 接口
 * @author xiaoxu123
 */
public interface LoginLogService {

    /**
     * 获得登录日志分页
     *
     * @param reqVO 分页条件
     * @return 登录日志分页
     */
    PageResult<LoginLogDO> getLoginLogPage(LoginLogPageReqVO reqVO);

    /**
     * 创建登录日志
     *
     * @param reqDTO 日志信息
     */
    void createLoginLog(@Valid LoginLogCreateReqDTO reqDTO);

}
