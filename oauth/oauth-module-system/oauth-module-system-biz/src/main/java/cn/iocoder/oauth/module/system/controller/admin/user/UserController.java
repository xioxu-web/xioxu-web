package cn.iocoder.oauth.module.system.controller.admin.user;
import cn.hutool.core.collection.CollUtil;
import cn.iocoder.oauth.framework.common.enums.CommonStatusEnum;
import cn.iocoder.oauth.framework.common.pojo.CommonResult;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.security.core.annotations.PreAuthenticated;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.user.*;
import cn.iocoder.oauth.module.system.convert.user.UserConvert;
import cn.iocoder.oauth.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.oauth.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.oauth.module.system.service.dept.DeptService;
import cn.iocoder.oauth.module.system.service.user.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import static cn.iocoder.oauth.framework.common.pojo.CommonResult.success;
import static cn.iocoder.oauth.framework.common.util.collection.CollectionUtils.convertList;

/**
 * @author xiaoxu123
 */
@Api(tags = "管理后台 - 用户")
@RestController
@RequestMapping("/system/user")
@Validated
public class UserController {
    
    @Resource
    private AdminUserService userService;

    @Resource
    private DeptService deptService;

    @GetMapping("/page")
    @ApiOperation("获得用户分页列表")
    @PreAuthorize("@ss.hasPermission('system:user:list')")
    public CommonResult<PageResult<UserPageItemRespVO>> getUserPage(@Valid UserPageReqVO reqVO) {
        // 获得用户分页列表
        PageResult<AdminUserDO> pageResult = userService.getUserPage(reqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal()));
        }
        // 获得拼接需要的数据
        Collection<Long> deptIds = convertList(pageResult.getList(), AdminUserDO::getDeptId );
        Map<Long, DeptDO> deptMap = deptService.getDeptMap(deptIds);
        // 拼接结果返回
        List<UserPageItemRespVO> userList = new ArrayList<>( pageResult.getList().size() );
        pageResult.getList().forEach( user -> {
            UserPageItemRespVO respVO = UserConvert.INSTANCE.convert( user );
            respVO.setDept(UserConvert.INSTANCE.convert(deptMap.get(user.getDeptId())));
            userList.add( respVO );
        } );
        return success(new PageResult<>(userList, pageResult.getTotal()));
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获取用户精简信息列表", notes = "只包含被开启的用户,主要用于前端的下拉选项")
    public CommonResult<List<UserSimpleRespVO>> getSimpleUsers() {
        // 获用户门列表，只要开启状态的
        List<AdminUserDO> list = userService.getUsersByStatus(CommonStatusEnum.ENABLE.getStatus());
        // 排序后，返回给前端
        return success(UserConvert.INSTANCE.convertList04(list));
    }

    @GetMapping("/get")
    @ApiOperation("获得用户详情")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:user:query')")
    public CommonResult<UserRespVO> getInfo(@RequestParam("id") Long id) {
        return success(UserConvert.INSTANCE.convert(userService.getUser(id)));
    }

    @PostMapping("/create")
    @ApiOperation("新增用户")
    @PreAuthorize("@ss.hasPermission('system:user:create')")
    public CommonResult<Long> createUser(@Valid @RequestBody UserCreateReqVO reqVO) {
        Long id = userService.createUser( reqVO );
        return success( id );
    }
    /**
     * 修改用户信息
     */
    @PutMapping("/update")
    @ApiOperation("修改用户")
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<Boolean> updateUser(@Valid @RequestBody UserUpdateReqVO reqVO){
      userService.updateUser(reqVO);
      return success(true);
    }

    @PutMapping("/update-password")
    @ApiOperation("重置用户密码")
    @PreAuthorize("@ss.hasPermission('system:user:update-password')")
    public CommonResult<Boolean> updateUserPassword(@Valid @RequestBody UserUpdatePasswordReqVO reqVO){
      userService.updateUserPassword( reqVO.getId(), reqVO.getPassword() );
      return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改用户状态")
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<Boolean> updateUserStatus(@Valid @RequestBody UserUpdateStatusReqVO reqVO) {
        userService.updateUserStatus( reqVO.getId(), reqVO.getStatus() );
        return success( true );
    }

}
