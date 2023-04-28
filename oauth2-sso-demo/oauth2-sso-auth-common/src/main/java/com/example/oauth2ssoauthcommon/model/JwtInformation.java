package com.example.oauth2ssoauthcommon.model;

import lombok.Data;

/**
 * @author xiaoxu123
 * @deprecated  jwt存储令牌信息
 */
@Data
public class JwtInformation {

    /**
     * JWT令牌唯一ID
     */
    private String jti;

    /**
     * 过期时间，单位秒，距离过期时间还有多少秒
     */
    private Long expireIn;


}
