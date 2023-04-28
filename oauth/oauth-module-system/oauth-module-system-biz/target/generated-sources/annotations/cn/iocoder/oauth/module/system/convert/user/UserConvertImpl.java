package cn.iocoder.oauth.module.system.convert.user;

import cn.iocoder.oauth.module.system.api.user.dto.AdminUserRespDTO;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.profile.UserProfileRespVO;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.profile.UserProfileRespVO.Post;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.profile.UserProfileRespVO.Role;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.user.UserCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.user.UserPageItemRespVO;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.user.UserPageItemRespVO.Dept;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.user.UserSimpleRespVO;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.user.UserUpdateReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.oauth.module.system.dal.dataobject.dept.PostDO;
import cn.iocoder.oauth.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.oauth.module.system.dal.dataobject.user.AdminUserDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-26T17:05:15+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class UserConvertImpl implements UserConvert {

    @Override
    public UserPageItemRespVO convert(AdminUserDO bean) {
        if ( bean == null ) {
            return null;
        }

        UserPageItemRespVO userPageItemRespVO = new UserPageItemRespVO();

        userPageItemRespVO.setUsername( bean.getUsername() );
        userPageItemRespVO.setNickname( bean.getNickname() );
        userPageItemRespVO.setRemark( bean.getRemark() );
        userPageItemRespVO.setDeptId( bean.getDeptId() );
        Set<Long> set = bean.getPostIds();
        if ( set != null ) {
            userPageItemRespVO.setPostIds( new HashSet<Long>( set ) );
        }
        userPageItemRespVO.setEmail( bean.getEmail() );
        userPageItemRespVO.setMobile( bean.getMobile() );
        userPageItemRespVO.setSex( bean.getSex() );
        userPageItemRespVO.setAvatar( bean.getAvatar() );
        userPageItemRespVO.setId( bean.getId() );
        userPageItemRespVO.setStatus( bean.getStatus() );
        userPageItemRespVO.setLoginIp( bean.getLoginIp() );
        userPageItemRespVO.setLoginDate( bean.getLoginDate() );
        userPageItemRespVO.setCreateTime( bean.getCreateTime() );

        return userPageItemRespVO;
    }

    @Override
    public Dept convert(DeptDO bean) {
        if ( bean == null ) {
            return null;
        }

        Dept dept = new Dept();

        dept.setId( bean.getId() );
        dept.setName( bean.getName() );

        return dept;
    }

    @Override
    public AdminUserDO convert(UserCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        AdminUserDO adminUserDO = new AdminUserDO();

        adminUserDO.setUsername( bean.getUsername() );
        adminUserDO.setPassword( bean.getPassword() );
        adminUserDO.setNickname( bean.getNickname() );
        adminUserDO.setRemark( bean.getRemark() );
        adminUserDO.setDeptId( bean.getDeptId() );
        Set<Long> set = bean.getPostIds();
        if ( set != null ) {
            adminUserDO.setPostIds( new HashSet<Long>( set ) );
        }
        adminUserDO.setEmail( bean.getEmail() );
        adminUserDO.setMobile( bean.getMobile() );
        adminUserDO.setSex( bean.getSex() );
        adminUserDO.setAvatar( bean.getAvatar() );

        return adminUserDO;
    }

    @Override
    public AdminUserDO convert(UserUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        AdminUserDO adminUserDO = new AdminUserDO();

        adminUserDO.setId( bean.getId() );
        adminUserDO.setUsername( bean.getUsername() );
        adminUserDO.setNickname( bean.getNickname() );
        adminUserDO.setRemark( bean.getRemark() );
        adminUserDO.setDeptId( bean.getDeptId() );
        Set<Long> set = bean.getPostIds();
        if ( set != null ) {
            adminUserDO.setPostIds( new HashSet<Long>( set ) );
        }
        adminUserDO.setEmail( bean.getEmail() );
        adminUserDO.setMobile( bean.getMobile() );
        adminUserDO.setSex( bean.getSex() );
        adminUserDO.setAvatar( bean.getAvatar() );

        return adminUserDO;
    }

    @Override
    public UserProfileRespVO convert03(AdminUserDO bean) {
        if ( bean == null ) {
            return null;
        }

        UserProfileRespVO userProfileRespVO = new UserProfileRespVO();

        userProfileRespVO.setUsername( bean.getUsername() );
        userProfileRespVO.setNickname( bean.getNickname() );
        userProfileRespVO.setRemark( bean.getRemark() );
        userProfileRespVO.setDeptId( bean.getDeptId() );
        Set<Long> set = bean.getPostIds();
        if ( set != null ) {
            userProfileRespVO.setPostIds( new HashSet<Long>( set ) );
        }
        userProfileRespVO.setEmail( bean.getEmail() );
        userProfileRespVO.setMobile( bean.getMobile() );
        userProfileRespVO.setSex( bean.getSex() );
        userProfileRespVO.setAvatar( bean.getAvatar() );
        userProfileRespVO.setId( bean.getId() );
        userProfileRespVO.setStatus( bean.getStatus() );
        userProfileRespVO.setLoginIp( bean.getLoginIp() );
        userProfileRespVO.setLoginDate( bean.getLoginDate() );
        userProfileRespVO.setCreateTime( bean.getCreateTime() );

        return userProfileRespVO;
    }

    @Override
    public List<Role> convertList(List<RoleDO> list) {
        if ( list == null ) {
            return null;
        }

        List<Role> list1 = new ArrayList<Role>( list.size() );
        for ( RoleDO roleDO : list ) {
            list1.add( roleDOToRole( roleDO ) );
        }

        return list1;
    }

    @Override
    public cn.iocoder.oauth.module.system.controller.admin.user.vo.profile.UserProfileRespVO.Dept convert02(DeptDO bean) {
        if ( bean == null ) {
            return null;
        }

        cn.iocoder.oauth.module.system.controller.admin.user.vo.profile.UserProfileRespVO.Dept dept = new cn.iocoder.oauth.module.system.controller.admin.user.vo.profile.UserProfileRespVO.Dept();

        dept.setId( bean.getId() );
        dept.setName( bean.getName() );

        return dept;
    }

    @Override
    public AdminUserDO convert(UserProfileUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        AdminUserDO adminUserDO = new AdminUserDO();

        adminUserDO.setNickname( bean.getNickname() );
        adminUserDO.setEmail( bean.getEmail() );
        adminUserDO.setMobile( bean.getMobile() );
        adminUserDO.setSex( bean.getSex() );

        return adminUserDO;
    }

    @Override
    public AdminUserDO convert(UserProfileUpdatePasswordReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        AdminUserDO adminUserDO = new AdminUserDO();

        return adminUserDO;
    }

    @Override
    public List<Post> convertList02(List<PostDO> list) {
        if ( list == null ) {
            return null;
        }

        List<Post> list1 = new ArrayList<Post>( list.size() );
        for ( PostDO postDO : list ) {
            list1.add( postDOToPost( postDO ) );
        }

        return list1;
    }

    @Override
    public List<UserSimpleRespVO> convertList04(List<AdminUserDO> list) {
        if ( list == null ) {
            return null;
        }

        List<UserSimpleRespVO> list1 = new ArrayList<UserSimpleRespVO>( list.size() );
        for ( AdminUserDO adminUserDO : list ) {
            list1.add( adminUserDOToUserSimpleRespVO( adminUserDO ) );
        }

        return list1;
    }

    @Override
    public AdminUserRespDTO convert4(AdminUserDO bean) {
        if ( bean == null ) {
            return null;
        }

        AdminUserRespDTO adminUserRespDTO = new AdminUserRespDTO();

        adminUserRespDTO.setId( bean.getId() );
        adminUserRespDTO.setNickname( bean.getNickname() );
        adminUserRespDTO.setStatus( bean.getStatus() );
        adminUserRespDTO.setDeptId( bean.getDeptId() );
        Set<Long> set = bean.getPostIds();
        if ( set != null ) {
            adminUserRespDTO.setPostIds( new HashSet<Long>( set ) );
        }
        adminUserRespDTO.setMobile( bean.getMobile() );

        return adminUserRespDTO;
    }

    @Override
    public List<AdminUserRespDTO> convertList4(List<AdminUserDO> users) {
        if ( users == null ) {
            return null;
        }

        List<AdminUserRespDTO> list = new ArrayList<AdminUserRespDTO>( users.size() );
        for ( AdminUserDO adminUserDO : users ) {
            list.add( convert4( adminUserDO ) );
        }

        return list;
    }

    @Override
    public Map<Long, AdminUserRespDTO> convertMap4(Map<Long, AdminUserDO> map) {
        if ( map == null ) {
            return null;
        }

        Map<Long, AdminUserRespDTO> map1 = new HashMap<Long, AdminUserRespDTO>( Math.max( (int) ( map.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<Long, AdminUserDO> entry : map.entrySet() ) {
            Long key = entry.getKey();
            AdminUserRespDTO value = convert4( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }

    protected Role roleDOToRole(RoleDO roleDO) {
        if ( roleDO == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( roleDO.getId() );
        role.setName( roleDO.getName() );

        return role;
    }

    protected Post postDOToPost(PostDO postDO) {
        if ( postDO == null ) {
            return null;
        }

        Post post = new Post();

        post.setId( postDO.getId() );
        post.setName( postDO.getName() );

        return post;
    }

    protected UserSimpleRespVO adminUserDOToUserSimpleRespVO(AdminUserDO adminUserDO) {
        if ( adminUserDO == null ) {
            return null;
        }

        UserSimpleRespVO userSimpleRespVO = new UserSimpleRespVO();

        userSimpleRespVO.setId( adminUserDO.getId() );
        userSimpleRespVO.setNickname( adminUserDO.getNickname() );

        return userSimpleRespVO;
    }
}
