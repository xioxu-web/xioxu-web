package io.microservice.oauth2.cloud.admin.biz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.microservice.oauth2.cloud.admin.api.dto.UserInfo;
import io.microservice.oauth2.cloud.admin.api.entity.SysPost;
import io.microservice.oauth2.cloud.admin.api.entity.SysUser;
import io.microservice.oauth2.cloud.admin.api.vo.UserInfoVO;
import io.microservice.oauth2.cloud.admin.api.vo.UserVO;
import io.microservice.oauth2.cloud.admin.biz.service.SysUserService;
import io.microservice.oauth2.cloud.common.security.annotation.Inner;
import io.microservice.oauth2.cloud.common.security.util.SecurityUtils;
import io.microservice.oauth2.common.core.exception.ErrorCodes;
import io.microservice.oauth2.common.core.util.MsgUtils;
import io.microservice.oauth2.common.core.util.ResultMsg;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.query;

/**
 * @author xiaoxu123
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "用户管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class UserController {

    @Resource
    private final SysUserService sysUserService;

    /**
     *获取当前用户全部信息
     * @retrun 全部用户
     */
    @GetMapping("/info")
    public ResultMsg<UserInfoVO> info(){
        String username = SecurityUtils.getUser().getUsername();
        SysUser sysUser= sysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));
        if(sysUser==null) {
            return ResultMsg.failed( MsgUtils.getMessage( ErrorCodes.SYS_USER_QUERY_ERROR ) );
        }
        UserInfo userInfo = sysUserService.getUserInfo( sysUser );
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setSysUser(userInfo.getSysUser());
        userInfoVO.setRoles(userInfo.getRoles());
        userInfoVO.setPermissions(userInfo.getPermissions());
        return ResultMsg.ok(userInfoVO);
    }

    @Inner
    @GetMapping("/info/{username}")
    public ResultMsg<UserInfo> info(@PathVariable("username") String username){
        SysUser sysUser = sysUserService.getOne( Wrappers.<SysUser>query().lambda().eq( SysUser::getUsername, username ) );
        if(sysUser==null){
           return ResultMsg.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_QUERY_ERROR,username));
        }
        return ResultMsg.ok( sysUserService.getUserInfo(sysUser));
    }

    /**
     * 通过ID查询用户信息
     * @param userId
     */
    @GetMapping("/user/{id:\\d+}")
    public ResultMsg<UserVO> user(@PathVariable Long userId){
        return ResultMsg.ok(sysUserService.getUserVoById(userId));
    }

    /**
     * 通过username查询用户信息
     */
    @GetMapping("/details/{username}")
    public ResultMsg<SysUser> user(@PathVariable("username") String username) {
        SysUser condition = new SysUser();
        condition.setUsername( username );
        return ResultMsg.ok( sysUserService.getOne( new QueryWrapper<>( condition ) ) );
    }


}
