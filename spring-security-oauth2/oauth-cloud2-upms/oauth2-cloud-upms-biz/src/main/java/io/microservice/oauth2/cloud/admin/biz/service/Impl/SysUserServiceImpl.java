package io.microservice.oauth2.cloud.admin.biz.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.microservice.oauth2.cloud.admin.api.dto.UserDTO;
import io.microservice.oauth2.cloud.admin.api.dto.UserInfo;
import io.microservice.oauth2.cloud.admin.api.entity.SysUser;
import io.microservice.oauth2.cloud.admin.api.vo.UserVO;
import io.microservice.oauth2.cloud.admin.biz.mapper.SysRoleMapper;
import io.microservice.oauth2.cloud.admin.biz.mapper.SysUserMapper;
import io.microservice.oauth2.cloud.admin.biz.mapper.SysUserRoleMapper;
import io.microservice.oauth2.cloud.admin.biz.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private final SysRoleMapper sysRoleMapper;

    private final SysUserRoleMapper sysUserRoleMapper;


    @Override
    public UserInfo getUserInfo(SysUser sysUser) {
        UserInfo userInfo = new UserInfo();

        return null;
    }

    @Override
    public UserVO getUserVoById(Long id) {
        return baseMapper.getUserVoById(id);
    }

    @Override
    public IPage<UserVO> getUserWithRolePage(Page page, UserDTO userDTO) {
        return null;
    }
}
