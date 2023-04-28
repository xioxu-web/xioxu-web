package cn.iocoder.oauth.module.system.controller.admin.user;
import cn.hutool.core.collection.CollUtil;
import cn.iocoder.oauth.framework.common.pojo.CommonResult;
import cn.iocoder.oauth.framework.datapermission.core.annotation.DataPermission;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.profile.UserProfileRespVO;
import cn.iocoder.oauth.module.system.convert.user.UserConvert;
import cn.iocoder.oauth.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.oauth.module.system.dal.dataobject.dept.PostDO;
import cn.iocoder.oauth.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.oauth.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.oauth.module.system.service.dept.DeptService;
import cn.iocoder.oauth.module.system.service.dept.PostService;
import cn.iocoder.oauth.module.system.service.permission.PermissionService;
import cn.iocoder.oauth.module.system.service.permission.RoleService;
import cn.iocoder.oauth.module.system.service.user.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static cn.iocoder.oauth.framework.common.pojo.CommonResult.success;
import static cn.iocoder.oauth.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * @author xiaoxu123
 */
@Api(tags = "管理后台 - 用户个人中心")
@RestController
@RequestMapping("/system/user/profile")
@Validated
@Slf4j
public class UserProfileController {
    @Resource
    private AdminUserService userService;
    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;


  @GetMapping("/get")
  @ApiOperation("获得登录用户信息")
  @DataPermission(enable = false) // 关闭数据权限，避免只查看自己时，查询不到部门。
  public CommonResult<UserProfileRespVO> profile(){
      //获取用户基本信息
      AdminUserDO user = userService.getUser( getLoginUserId() );
      UserProfileRespVO resp = UserConvert.INSTANCE.convert03( user );
      //获得用户角色
      List<RoleDO> userRoles = roleService.getRolesFromCache(permissionService.getUserRoleIdListByUserId(user.getId()));
      resp.setRoles(UserConvert.INSTANCE.convertList(userRoles));
      //获得部门信息
      if(user.getDeptId()!=null){
          DeptDO dept = deptService.getDept( user.getDeptId() );
          resp.setDept(UserConvert.INSTANCE.convert02(dept));
      }
      //获取岗位信息
      if(CollUtil.isNotEmpty(user.getPostIds())){
          List<PostDO> posts = postService.getPosts( user.getPostIds() );
          resp.setPosts(UserConvert.INSTANCE.convertList02(posts));
      }
       return success(resp);
  }
}
