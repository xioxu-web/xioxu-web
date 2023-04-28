package com.example.oauth2ssoauthserver.service;

import com.example.oauth2ssoauthserver.entity.SysUser;

/**
 * @author xiaoxu123
 */
public interface UserService {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    SysUser getByUsername(String username);
}
