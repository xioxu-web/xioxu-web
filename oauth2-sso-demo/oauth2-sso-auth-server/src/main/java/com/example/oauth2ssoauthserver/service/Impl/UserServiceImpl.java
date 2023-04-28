package com.example.oauth2ssoauthserver.service.Impl;
import com.example.oauth2ssoauthserver.entity.SysUser;
import com.example.oauth2ssoauthserver.repository.SysUserRepository;
import com.example.oauth2ssoauthserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaoxu123
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserRepository sysUserRepository;


    @Override
    public SysUser getByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }
}
