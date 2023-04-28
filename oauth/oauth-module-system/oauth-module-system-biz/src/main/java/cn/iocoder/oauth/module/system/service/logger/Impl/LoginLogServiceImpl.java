package cn.iocoder.oauth.module.system.service.logger.Impl;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.iocoder.oauth.module.system.controller.admin.logger.vo.loginlog.LoginLogPageReqVO;
import cn.iocoder.oauth.module.system.convert.logger.LoginLogConvert;
import cn.iocoder.oauth.module.system.dal.dataobject.logger.LoginLogDO;
import cn.iocoder.oauth.module.system.dal.mysql.logger.LoginLogMapper;
import cn.iocoder.oauth.module.system.service.logger.LoginLogService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;


/**
 * @author xiaoxu123
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {
    @Resource
    private LoginLogMapper loginLogMapper;

    @Override
    public PageResult<LoginLogDO> getLoginLogPage(LoginLogPageReqVO reqVO) {
        return loginLogMapper.selectPage(reqVO);
    }


    @Override
    public void createLoginLog(LoginLogCreateReqDTO reqDTO) {
        LoginLogDO loginLog = LoginLogConvert.INSTANCE.convert(reqDTO);
        loginLogMapper.insert(loginLog);
    }
}
