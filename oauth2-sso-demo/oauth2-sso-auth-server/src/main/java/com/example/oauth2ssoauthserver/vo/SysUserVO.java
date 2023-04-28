package com.example.oauth2ssoauthserver.vo;

import com.example.oauth2ssoauthserver.entity.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class SysUserVO extends SysUser {

    /**
     * 权限列表
     */
    private List<String> authorityList;

}
