package cn.iocoder.oauth.module.system.service.auth.Impl;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.oauth.framework.common.enums.CommonStatusEnum;
import cn.iocoder.oauth.framework.common.enums.UserTypeEnum;
import cn.iocoder.oauth.framework.common.util.ValidationUtils;
import cn.iocoder.oauth.framework.common.util.monitor.TracerUtils;
import cn.iocoder.oauth.framework.common.util.servlet.ServletUtils;
import cn.iocoder.oauth.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.iocoder.oauth.module.system.controller.admin.auth.vo.AuthLoginReqVO;
import cn.iocoder.oauth.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import cn.iocoder.oauth.module.system.convert.auth.AuthConvert;
import cn.iocoder.oauth.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.iocoder.oauth.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.oauth.module.system.enums.auth.OAuth2ClientConstants;
import cn.iocoder.oauth.module.system.enums.logger.LoginLogTypeEnum;
import cn.iocoder.oauth.module.system.enums.logger.LoginResultEnum;
import cn.iocoder.oauth.module.system.service.auth.AdminAuthService;
import cn.iocoder.oauth.module.system.service.common.CaptchaService;
import cn.iocoder.oauth.module.system.service.logger.LoginLogService;
import cn.iocoder.oauth.module.system.service.oauth2.OAuth2TokenService;
import cn.iocoder.oauth.module.system.service.user.AdminUserService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.Validator;
import static cn.iocoder.oauth.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.oauth.module.system.enums.ErrorCodeConstants.*;
/**
 * @author xiaoxu123
 */
@Service
@Slf4j
@Transactional(rollbackFor=Exception.class)
public class AdminAuthServiceImpl implements AdminAuthService {

    private Logger logger= LoggerFactory.getLogger(AdminAuthServiceImpl.class);
    @Resource
    private LoginLogService loginLogService;

    @Resource
    private AdminUserService userService;

    @Resource
    private CaptchaService captchaService;

    @Resource
    private OAuth2TokenService oauth2TokenService;

    @Resource
    private Validator validator;

    @Override
    public AdminUserDO authenticate(String username, String password) {
      final LoginLogTypeEnum logTypeEnum=LoginLogTypeEnum.LOGIN_USERNAME;
      //校验账号是否存在
        AdminUserDO user = userService.getUserByUsername(username);
        if(user==null){
            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        return user;
    }

    @Override
    public AuthLoginRespVO login(AuthLoginReqVO reqVO) {
        // 判断验证码是否正确
        verifyCaptcha(reqVO);
        // 使用账号密码，进行登录
        AdminUserDO user = authenticate(reqVO.getUsername(), reqVO.getPassword());
        logger.info("AdminAuthServiceImpl#login get success :{}",JSONObject.toJSONString(user));
        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME);
    }

    @Override
    public void updateUserLogin(Long id, String loginIp) {

    }

    private void createLoginLog(Long userId, String username,
                                LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
        // 插入登录日志
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logTypeEnum.getType());
        reqDTO.setTraceId( TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(username);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(loginResult.getResult());
        loginLogService.createLoginLog(reqDTO);
    }

    @Override
    public AuthLoginRespVO refreshToken(String refreshToken) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.refreshAccessToken(refreshToken, OAuth2ClientConstants.CLIENT_ID_DEFAULT);
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    private AuthLoginRespVO createTokenAfterLoginSuccess(Long userId,String username,LoginLogTypeEnum logType){
      //插入登录日志
      createLoginLog(userId,username,logType,LoginResultEnum.SUCCESS);
        //创建令牌
        OAuth2AccessTokenDO accessToken = oauth2TokenService.createAccessToken( userId, getUserType().getValue(), OAuth2ClientConstants.CLIENT_ID_DEFAULT, null );
        //构建返回结果
        return AuthConvert.INSTANCE.convert(accessToken);
    }

    @Override
    public void logout(String token, Integer logType) {
        // 删除访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.removeAccessToken(token);
        if (accessTokenDO == null) {
            return;
        }
        // 删除成功，则记录登出日志
        createLogoutLog(accessTokenDO.getUserId(), accessTokenDO.getUserType(), logType );
    }
    private void createLogoutLog(Long userId, Integer userType, Integer logType) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logType);
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(userType);
        if (ObjectUtil.notEqual(getUserType(), userType)) {
            reqDTO.setUsername( getUsername( userId ) );
        }
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        loginLogService.createLoginLog(reqDTO);
    }

    private void verifyCaptcha(AuthLoginReqVO reqVO){
      if(!captchaService.isCaptchaEnable()){
         return;
      }
      //校验验证码
        ValidationUtils.validate(validator,reqVO, AuthLoginReqVO.CodeEnableGroup.class);
        // 验证码不存在
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        String code = captchaService.getCaptchaCode(reqVO.getUuid());
        if (code == null) {
            // 创建登录失败日志（验证码不存在）
            createLoginLog(null, reqVO.getUsername(), logTypeEnum, LoginResultEnum.CAPTCHA_NOT_FOUND);
            throw exception(AUTH_LOGIN_CAPTCHA_NOT_FOUND);
        }
        // 验证码不正确
        if (!code.equals(reqVO.getCode())) {
            // 创建登录失败日志（验证码不正确)
            createLoginLog(null, reqVO.getUsername(), logTypeEnum, LoginResultEnum.CAPTCHA_CODE_ERROR);
            throw exception(AUTH_LOGIN_CAPTCHA_CODE_ERROR);
        }
        // 正确，所以要删除下验证码
        captchaService.deleteCaptchaCode(reqVO.getUuid());

    }

    private String getUsername(Long userId) {
        if (userId == null) {
            return null;
        }
        AdminUserDO user = userService.getUser(userId);
        return user != null ? user.getUsername() : null;
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }
}
