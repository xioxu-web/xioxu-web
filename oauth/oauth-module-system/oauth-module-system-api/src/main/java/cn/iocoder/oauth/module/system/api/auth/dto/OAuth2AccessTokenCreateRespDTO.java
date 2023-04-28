package cn.iocoder.oauth.module.system.api.auth.dto;

import java.util.Date;

/**
 * OAuth2.0 访问令牌的信息 Response DTO
 * @author xiaoxu123
 */
public class OAuth2AccessTokenCreateRespDTO {
    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     *用户编号
     */
    private String userId;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 过期时间
     */
    private Date expiresTime;

}
