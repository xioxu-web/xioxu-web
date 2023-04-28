package io.microservices.shop.oauth.util;

import java.io.Serializable;

/**
 * @author xiaoxu123
 * @Description: 用户token令牌封装工具类
 */
public class AuthToken implements Serializable {

    /**
     * 令牌信息
     */
    String accessToken;

    /**
     * 刷新token(refresh_token)
     */
    String refreshToken;

    /**
     * jwt短令牌
     */
    String jti;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }
}