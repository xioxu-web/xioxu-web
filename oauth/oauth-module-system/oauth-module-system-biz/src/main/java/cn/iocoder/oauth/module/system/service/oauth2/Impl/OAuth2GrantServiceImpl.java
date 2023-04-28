package cn.iocoder.oauth.module.system.service.oauth2.Impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.oauth.framework.common.enums.UserTypeEnum;
import cn.iocoder.oauth.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.iocoder.oauth.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.oauth.module.system.service.auth.AdminAuthService;
import cn.iocoder.oauth.module.system.service.oauth2.OAuth2GrantService;
import cn.iocoder.oauth.module.system.service.oauth2.OAuth2TokenService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * OAuth2 授予 Service 实现类
 *
 * @author 芋道源码
 */
@Service
public class OAuth2GrantServiceImpl implements OAuth2GrantService {

    @Resource
    private OAuth2TokenService oauth2TokenService;
    @Resource
    private AdminAuthService adminAuthService;

    @Override
    public
    OAuth2AccessTokenDO grantImplicit(Long userId, Integer userType, String clientId, List<String> scopes) {
        return null;
    }

    @Override
    public
    String grantAuthorizationCodeForCode(Long userId, Integer userType, String clientId, List<String> scopes, String redirectUri, String state) {
        return null;
    }

    @Override
    public
    OAuth2AccessTokenDO grantAuthorizationCodeForAccessToken(String clientId, String code, String redirectUri, String state) {
        return null;
    }

    @Override
    public
    OAuth2AccessTokenDO grantPassword(String username, String password, String clientId, List<String> scopes) {
        // 使用账号 + 密码进行登录
        AdminUserDO user = adminAuthService.authenticate(username, password);
        Assert.notNull(user, "用户不能为空！"); // 防御性编程

        // 创建访问令牌
        return oauth2TokenService.createAccessToken(user.getId(), UserTypeEnum.ADMIN.getValue(), clientId, scopes);
    }

    @Override
    public OAuth2AccessTokenDO grantRefreshToken(String refreshToken, String clientId) {
        return oauth2TokenService.refreshAccessToken(refreshToken, clientId);
    }

    @Override
    public
    OAuth2AccessTokenDO grantClientCredentials(String clientId, List<String> scopes) {
        return null;
    }

    @Override
    public boolean revokeToken(String clientId, String accessToken) {
        // 先查询，保证 clientId 时匹配的
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.getAccessToken(accessToken);
        if (accessTokenDO == null || ObjectUtil.notEqual(clientId, accessTokenDO.getClientId())) {
            return false;
        }
        // 再删除
        return oauth2TokenService.removeAccessToken(accessToken) != null;
    }

}
