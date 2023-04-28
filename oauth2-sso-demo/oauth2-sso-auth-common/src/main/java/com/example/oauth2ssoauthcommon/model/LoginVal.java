package com.example.oauth2ssoauthcommon.model;

import lombok.Data;

/**
 * @author xiaoxu123
 * @deprecated 用户登录信息
 */
@Data
public class LoginVal extends JwtInformation {

    private String userId;

    private String username;

    private String[] authorities;


}
