package cn.iocoder.oauth.module.system.controller.admin.auth;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.oauth.framework.common.enums.CommonStatusEnum;
import cn.iocoder.oauth.framework.common.pojo.CommonResult;
import cn.iocoder.oauth.framework.common.util.collection.SetUtils;
import cn.iocoder.oauth.framework.security.config.SecurityProperties;
import cn.iocoder.oauth.module.system.controller.admin.auth.vo.AuthLoginReqVO;
import cn.iocoder.oauth.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import cn.iocoder.oauth.module.system.controller.admin.auth.vo.AuthMenuRespVO;
import cn.iocoder.oauth.module.system.controller.admin.auth.vo.AuthPermissionInfoRespVO;
import cn.iocoder.oauth.module.system.convert.auth.AuthConvert;
import cn.iocoder.oauth.module.system.dal.dataobject.permission.MenuDO;
import cn.iocoder.oauth.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.oauth.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.oauth.module.system.enums.logger.LoginLogTypeEnum;
import cn.iocoder.oauth.module.system.enums.permission.MenuTypeEnum;
import cn.iocoder.oauth.module.system.service.auth.AdminAuthService;
import cn.iocoder.oauth.module.system.service.permission.PermissionService;
import cn.iocoder.oauth.module.system.service.permission.RoleService;
import cn.iocoder.oauth.module.system.service.user.AdminUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import static cn.iocoder.oauth.framework.common.pojo.CommonResult.success;
import static cn.iocoder.oauth.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.oauth.framework.security.core.util.SecurityFrameworkUtils.obtainAuthorization;
import static java.util.Collections.singleton;

/**
 * @author xiaoxu123
 */
@Api(tags = "管理后台 - 认证")
@RestController
@RequestMapping("/system/auth")
@Validated
@Slf4j
public class AuthController {

    @Resource
    private AdminAuthService authService;
    @Resource
    private AdminUserService userService;
    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private SecurityProperties securityProperties;

    @PostMapping("/login")
    @ApiOperation("使用账号密码登录")
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO) {
        return success(authService.login(reqVO));
    }
    @PostMapping("/logout")
    @ApiOperation("登出系统")
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        String token = obtainAuthorization(request, securityProperties.getTokenHeader());
        if (StrUtil.isNotBlank(token)) {
            authService.logout(token, LoginLogTypeEnum.LOGOUT_SELF.getType());
        }
        return success(true);
    }

    /**
     * 获取登录用户的权限信息
     */
    @GetMapping("/get-permission-info")
    @ApiOperation("获取登录用户的权限信息")
    public CommonResult<AuthPermissionInfoRespVO> getPermissionInfo() {
        // 获得用户信息
        AdminUserDO user = userService.getUser(getLoginUserId());
        if (user == null) {
            return null;
        }
        // 获得角色列表
        Set<Long> roleIds = permissionService.getUserRoleIdsFromCache(getLoginUserId(), singleton(CommonStatusEnum.ENABLE.getStatus()));
        List<RoleDO> roleList = roleService.getRolesFromCache(roleIds);
        // 获得菜单列表
        List<MenuDO> menuList = permissionService.getRoleMenuListFromCache(roleIds,
                SetUtils.asSet(MenuTypeEnum.DIR.getType(), MenuTypeEnum.MENU.getType(), MenuTypeEnum.BUTTON.getType()),
                singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 拼接结果返回
        return success(AuthConvert.INSTANCE.convert(user,roleList,menuList));
    }

    @GetMapping("/list-menus")
    @ApiOperation("获得登录用户的菜单列表")
    public CommonResult<List<AuthMenuRespVO>> getMenus() {
      // 获得角色列表
        Set<Long> roleIds = permissionService.getUserRoleIdsFromCache(getLoginUserId(), singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 获得用户拥有的菜单列表
        List<MenuDO> menuList = permissionService.getRoleMenuListFromCache(roleIds,
                SetUtils.asSet(MenuTypeEnum.DIR.getType(), MenuTypeEnum.MENU.getType()),
                singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 转换成 Tree 结构返回
        return success(AuthConvert.INSTANCE.buildMenuTree(menuList));
    }

    @PostMapping("/refresh-token")
    @ApiOperation("刷新令牌")
    @ApiImplicitParam(name = "refreshToken", value = "刷新令牌", required = true, dataTypeClass = String.class)
    public CommonResult<AuthLoginRespVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return success(authService.refreshToken(refreshToken));
    }
}
