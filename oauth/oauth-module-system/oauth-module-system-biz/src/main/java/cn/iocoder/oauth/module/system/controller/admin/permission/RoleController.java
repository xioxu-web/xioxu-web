package cn.iocoder.oauth.module.system.controller.admin.permission;
import cn.iocoder.oauth.framework.common.enums.CommonStatusEnum;
import cn.iocoder.oauth.framework.common.pojo.CommonResult;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.system.controller.admin.permission.vo.role.RolePageReqVO;
import cn.iocoder.oauth.module.system.controller.admin.permission.vo.role.RoleRespVO;
import cn.iocoder.oauth.module.system.controller.admin.permission.vo.role.RoleSimpleRespVO;
import cn.iocoder.oauth.module.system.convert.permission.RoleConvert;
import cn.iocoder.oauth.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.oauth.module.system.service.permission.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static cn.iocoder.oauth.framework.common.pojo.CommonResult.success;


/**
 * @author xiaoxu123
 */
@Api(tags = "管理后台 - 角色")
@RestController
@RequestMapping("/system/role")
@Validated
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("/get")
    @ApiOperation("获得角色信息")
    @PreAuthorize("@ss.hasPermission('system:role:query')")
    public CommonResult<RoleRespVO> getRole(@RequestParam("id") Long id) {
        RoleDO role = roleService.getRole(id);
        return success(RoleConvert.INSTANCE.convert(role));
    }

    @GetMapping("/page")
    @ApiOperation("获得角色分页")
    @PreAuthorize("@ss.hasPermission('system:role:query')")
    public CommonResult<PageResult<RoleDO>> getRolePage(RolePageReqVO reqVO) {
        return success(roleService.getRolePage(reqVO));
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获取角色精简信息列表", notes = "只包含被开启的角色，主要用于前端的下拉选项")
    public CommonResult<List<RoleSimpleRespVO>> getSimpleRoles() {
        // 获得角色列表，只要开启状态的
        List<RoleDO> list = roleService.getRoles( Collections.singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 排序后，返回给前端
        list.sort( Comparator.comparing(RoleDO::getSort));
        return success(RoleConvert.INSTANCE.convertList02(list));
    }


}
