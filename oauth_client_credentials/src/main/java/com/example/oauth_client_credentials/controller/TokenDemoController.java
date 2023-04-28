package com.example.oauth_client_credentials.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;

/**
 * 删除令牌控制器
 * @author xiaoxu123
 */
@RestController
@RequestMapping("/token/demo")
public class TokenDemoController {

    @Qualifier("consumerTokenServices")
    @Autowired
    private ConsumerTokenServices tokenServices;

    @PostMapping("/revokeToken")
    public boolean revokeToken(@RequestParam("token")String token) {
        return tokenServices.revokeToken(token);
    }

}
