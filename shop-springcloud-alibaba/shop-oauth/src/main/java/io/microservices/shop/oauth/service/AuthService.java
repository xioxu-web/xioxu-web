package io.microservices.shop.oauth.service;

import io.microservices.shop.oauth.util.AuthToken;

public interface AuthService {

    /***
     * 授权认证方法
     * @param clientId
     * @param clientSecret
     * @param username
     * @param password
     * @return
     */
    AuthToken login(String username, String password, String clientId, String clientSecret);

}
