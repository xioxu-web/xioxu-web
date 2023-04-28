package io.microservice.oauth2.cloud.admin.api.dto;

import io.microservice.oauth2.cloud.admin.api.entity.SysPost;
import lombok.Data;

import java.util.List;

/**
 * @author xiaoxu123
 */
@Data
public class UserInfo {

    /**
     * 用户基本信息
     */
    private UserDTO sysUser;

    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 角色集合
     */
    private Long[] roles;

    /**
     * 角色集合
     */
    private List<SysRole> roleList;

    /**
     * 岗位集合
     */
    private Long[] posts;

    /**
     * 岗位集合
     */
    private List<SysPost> postList;

}
