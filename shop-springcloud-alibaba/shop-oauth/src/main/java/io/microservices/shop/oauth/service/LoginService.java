package io.microservices.shop.oauth.service;


import io.microservices.shop.oauth.util.AuthToken;

public interface LoginService {

    /**
     * 模拟用户的行为 发送请求 申请令牌 返回
     * @param username 用户名
     * @param password 用户密码
     * @param clientId 客户端id
     * @param clientSecret 客户端密钥
     * @param grandType 认证授权类型：password密码授权模式
     * @return
     */
    AuthToken login(String username, String password, String clientId, String clientSecret, String grandType);

}
