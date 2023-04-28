package io.microservices.shop.oauth.controller;

import io.microservices.shop.oauth.service.LoginService;
import io.microservices.shop.oauth.util.AuthToken;
import io.microservices.shop.oauth.util.CookieUtil;
import io.microservices.shop.common.constant.StatusCode;
import io.microservices.shop.common.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author xiaoxu123
 */
@RestController
@RequestMapping("/user")
public class UserLoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 客户端id
     */
    @Value("${auth.clientId}")
    private String clientId;

    /**
     * 客户端密钥
     */
    @Value("${auth.clientSecret}")
    private String clientSecret;

    /**
     * 授权模式 密码模式
     */
    private static final String GRAND_TYPE = "password";

    /**
     * cookie域名
     */
    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    /**
     * Cookie生命周期
     */
    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;


    /**
     * 登录方法：
     * 1.密码模式认证-授权方式：grant_type=password
     *
     * @param username 2.账号 szitheima
     * @param password 3.密码 szitheima
     * @return
     */
    @RequestMapping("/login")
    public Result<Map> login(String username, String password) {
        // 调用loginService的login方法进行登录，并返回生成的令牌数据
        AuthToken authToken = loginService.login(username, password, clientId, clientSecret, GRAND_TYPE);
        // 设置到cookie中
        saveCookie(authToken.getAccessToken());
        return new Result<>(true, StatusCode.OK, "令牌生成成功", authToken);
    }

    // 保存token到cookie中
    private void saveCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response, cookieDomain, "/", "Authorization", token, cookieMaxAge, false);
    }
}
