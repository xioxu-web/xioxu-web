package cn.iocoder.oauth.module.system.api.auth;
import cn.iocoder.oauth.module.system.api.auth.dto.OAuth2AccessTokenCheckRespDTO;
import cn.iocoder.oauth.module.system.api.auth.dto.OAuth2AccessTokenCreateReqDTO;
import cn.iocoder.oauth.module.system.api.auth.dto.OAuth2AccessTokenRespDTO;

import javax.validation.Valid;

/**
 * Oauth 2.0创建令牌接口
 * @author xiaoxu123
 */
public interface OAuth2TokenApi {
    /**
     *创建访问令牌
     * @param reqDTO
     * @return 访问令牌的信息
     */
  OAuth2AccessTokenRespDTO createAccessToken(@Valid OAuth2AccessTokenCreateReqDTO reqDTO);

    /**
     * 校验访问令牌
     * @param accessToken
     * @return
     */
    OAuth2AccessTokenCheckRespDTO checkAccessToken(String accessToken);

    /**
     * 删除访问令牌
     * @param accessToken
     * @return
     */
    OAuth2AccessTokenRespDTO deleteAccessToken(String accessToken);

  /**
   * 刷新访问令牌
   *
   * @param refreshToken 刷新令牌
   * @param clientId 客户端编号
   * @return 访问令牌的信息
   */
  OAuth2AccessTokenRespDTO refreshAccessToken(String refreshToken, String clientId);

}
