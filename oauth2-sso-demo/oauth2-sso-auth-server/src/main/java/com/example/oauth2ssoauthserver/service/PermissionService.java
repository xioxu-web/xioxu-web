package com.example.oauth2ssoauthserver.service;

import com.example.oauth2ssoauthserver.entity.SysPermission;

import java.util.List;

/**
 * @author xiaoxu123
 */
public interface PermissionService {

    List<SysPermission> findByUserId(Integer userId);

}
