package cn.iocoder.oauth.module.system.service.auth;


import cn.iocoder.oauth.module.system.controller.admin.auth.vo.AuthLoginReqVO;
import cn.iocoder.oauth.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import cn.iocoder.oauth.module.system.dal.dataobject.user.AdminUserDO;

import javax.validation.Valid;

/**
 * @author xiaoxu123
 */
public interface AdminAuthService {

    /**
     * 验证账号 + 密码。如果通过，则返回用户
     *
     * @param username 账号
     * @param password 密码
     * @return 用户
     */
    AdminUserDO authenticate(String username, String password);

    /**
     * 使用账号和密码登录
     */
   AuthLoginRespVO login(@Valid AuthLoginReqVO authLoginReqVO);

    /**
     * 更新用户的最后登陆信息
     *
     * @param id 用户编号
     * @param loginIp 登陆 IP
     */
    void updateUserLogin(Long id, String loginIp);

    /**
     * 刷新访问令牌
     *
     * @param refreshToken 刷新令牌
     * @return 登录结果
     */
    AuthLoginRespVO refreshToken(String refreshToken);

    /**
     * 基于 token 退出登录
     *
     * @param token token
     * @param logType 登出类型
     */
    void logout(String token, Integer logType);

}
