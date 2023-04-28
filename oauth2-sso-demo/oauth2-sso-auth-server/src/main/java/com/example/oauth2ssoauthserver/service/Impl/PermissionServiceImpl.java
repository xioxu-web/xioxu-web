package com.example.oauth2ssoauthserver.service.Impl;

import com.example.oauth2ssoauthserver.entity.SysPermission;
import com.example.oauth2ssoauthserver.entity.SysRolePermission;
import com.example.oauth2ssoauthserver.entity.SysUserRole;
import com.example.oauth2ssoauthserver.repository.SysPermissionRepository;
import com.example.oauth2ssoauthserver.repository.SysRolePermissionRepository;
import com.example.oauth2ssoauthserver.repository.SysUserRoleRepository;
import com.example.oauth2ssoauthserver.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaoxu123
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private
    SysUserRoleRepository sysUserRoleRepository;

    @Autowired
    private
    SysRolePermissionRepository sysRolePermissionRepository;

    @Autowired
    private SysPermissionRepository sysPermissionRepository;


    @Override
    public List<SysPermission> findByUserId(Integer userId) {
        List<SysUserRole> sysUserRoleList  = sysUserRoleRepository.findByUserId( userId );
        if(CollectionUtils.isEmpty(sysUserRoleList)){
            return null;
        }

        List<Integer> roleIdList  = sysUserRoleList.stream().map( SysUserRole::getRoleId ).collect( Collectors.toList() );

        List<SysRolePermission> rolePermissionList  = sysRolePermissionRepository.findByRoleIds( roleIdList );
        if(CollectionUtils.isEmpty(rolePermissionList)){
            return null;
        }

        List<Integer> permissionIdList  = rolePermissionList.stream().map( SysRolePermission::getRoleId ).distinct().collect( Collectors.toList() );

        List<SysPermission> sysPermissionList = sysPermissionRepository.findByIds( permissionIdList );

        if(CollectionUtils.isEmpty(sysPermissionList)){
            return null;
        }

        return sysPermissionList;
    }
}
