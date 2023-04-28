package cn.iocoder.oauth.module.system.controller.admin.permission;
import cn.iocoder.oauth.framework.common.enums.CommonStatusEnum;
import cn.iocoder.oauth.framework.common.pojo.CommonResult;
import cn.iocoder.oauth.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import cn.iocoder.oauth.module.system.controller.admin.permission.vo.menu.MenuRespVO;
import cn.iocoder.oauth.module.system.controller.admin.permission.vo.menu.MenuSimpleRespVO;
import cn.iocoder.oauth.module.system.convert.permission.MenuConvert;
import cn.iocoder.oauth.module.system.dal.dataobject.permission.MenuDO;
import cn.iocoder.oauth.module.system.service.permission.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import static cn.iocoder.oauth.framework.common.pojo.CommonResult.success;


/**
 * @author xiaoxu123
 */
@Api(tags = "管理后台 - 菜单")
@RestController
@RequestMapping("/system/menu")
@Validated
public class MenuController {
    @Resource
    private MenuService menuService;

    @GetMapping("/list")
    @ApiOperation(value = "获取菜单列表", notes = "用于【菜单管理】界面")
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public CommonResult<List<MenuRespVO>> getMenus(MenuListReqVO reqVO) {
        List<MenuDO> list = menuService.getMenus(reqVO);
        list.sort( Comparator.comparing(MenuDO::getSort));
        return success(MenuConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获取菜单精简信息列表", notes = "只包含被开启的菜单，用于【角色分配菜单】功能的选项")
    public CommonResult<List<MenuSimpleRespVO>> getSimpleMenus() {
        // 获得菜单列表，只要开启状态的
        MenuListReqVO reqVO = new MenuListReqVO();
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        List<MenuDO> list = menuService.getSystemMenus(reqVO);
        // 排序后，返回给前端
        list.sort(Comparator.comparing(MenuDO::getSort));
        return success(MenuConvert.INSTANCE.convertList02(list));
    }


    @GetMapping("/get")
    @ApiOperation("获取菜单信息")
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public CommonResult<MenuRespVO> getMenu(Long id) {
        MenuDO menu = menuService.getMenu(id);
        return success(MenuConvert.INSTANCE.convert(menu));
    }



}
