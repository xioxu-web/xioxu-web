package io.microservice.oauth2.cloud.admin.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.microservice.oauth2.cloud.admin.api.dto.UserDTO;
import io.microservice.oauth2.cloud.admin.api.dto.UserInfo;
import io.microservice.oauth2.cloud.admin.api.entity.SysUser;
import io.microservice.oauth2.cloud.admin.api.vo.UserVO;

import java.util.List;


/**
 * @author xiaoxu123
 * 用户业务类
 */
public interface SysUserService extends IService<SysUser>{

    /**
     * 查询用户信息
     * @param sysUser
     * @return UserInfo
     */
    UserInfo getUserInfo(SysUser sysUser);


    /**
     * 通过ID查询用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    UserVO getUserVoById(Long id);


    /**
     * 分页查询用户信息（含有角色信息）
     * @param page 分页对象
     * @param userDTO 参数列表
     * @return
     */
    IPage<UserVO> getUserWithRolePage(Page page, UserDTO userDTO);





}
