package cn.iocoder.oauth.module.system.convert.user;

import cn.iocoder.oauth.module.system.api.user.dto.AdminUserRespDTO;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.profile.UserProfileRespVO;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.user.UserCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.user.UserPageItemRespVO;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.user.UserSimpleRespVO;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.user.UserUpdateReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.oauth.module.system.dal.dataobject.dept.PostDO;
import cn.iocoder.oauth.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.oauth.module.system.dal.dataobject.user.AdminUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserPageItemRespVO convert(AdminUserDO bean);

    UserPageItemRespVO.Dept convert(DeptDO bean);

    AdminUserDO convert(UserCreateReqVO bean);

    AdminUserDO convert(UserUpdateReqVO bean);

    UserProfileRespVO convert03(AdminUserDO bean);

    List<UserProfileRespVO.Role> convertList(List<RoleDO> list);

    UserProfileRespVO.Dept convert02(DeptDO bean);

    AdminUserDO convert(UserProfileUpdateReqVO bean);

    AdminUserDO convert(UserProfileUpdatePasswordReqVO bean);

    List<UserProfileRespVO.Post> convertList02(List<PostDO> list);

    List<UserSimpleRespVO> convertList04(List<AdminUserDO> list);

    AdminUserRespDTO convert4(AdminUserDO bean);

    List<AdminUserRespDTO> convertList4(List<AdminUserDO> users);

    Map<Long, AdminUserRespDTO> convertMap4(Map<Long, AdminUserDO> map);

}
