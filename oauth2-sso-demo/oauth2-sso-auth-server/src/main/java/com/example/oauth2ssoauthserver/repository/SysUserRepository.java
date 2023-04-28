package com.example.oauth2ssoauthserver.repository;

import com.example.oauth2ssoauthserver.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author xiaoxu123
 */
@Repository
public interface SysUserRepository extends JpaSpecificationExecutor<SysUser>, JpaRepository<SysUser, Integer> {


    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    SysUser findByUsername(String username);
}
