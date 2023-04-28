package cn.iocoder.oauth.module.system.service.user.Impl;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.oauth.framework.common.enums.CommonStatusEnum;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.common.util.collection.CollectionUtils;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.user.vo.user.*;
import cn.iocoder.oauth.module.system.convert.user.UserConvert;
import cn.iocoder.oauth.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.oauth.module.system.dal.dataobject.dept.UserPostDO;
import cn.iocoder.oauth.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.oauth.module.system.dal.mysql.dept.UserPostMapper;
import cn.iocoder.oauth.module.system.dal.mysql.user.AdminUserMapper;
import cn.iocoder.oauth.module.system.service.dept.DeptService;
import cn.iocoder.oauth.module.system.service.permission.PermissionService;
import cn.iocoder.oauth.module.system.service.user.AdminUserService;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.InputStream;
import java.util.*;
import static cn.iocoder.oauth.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.oauth.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.oauth.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.iocoder.oauth.module.system.enums.ErrorCodeConstants.*;

/**
 * 后台用户 Service 实现类
 * @author 芋道源码
 */
@Service("adminUserService")
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    @Value("${sys.user.init-password:admin123}")
    private String userInitPassword;

    @Resource
    private AdminUserMapper userMapper;

    @Resource
    private DeptService deptService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserPostMapper userPostMapper;


    @Override
    @Transactional(rollbackFor =Exception.class)
    public Long createUser(@Valid UserCreateReqVO reqVO) {
        // 校验正确性
        checkCreateOrUpdate(null, reqVO.getUsername(), reqVO.getMobile(), reqVO.getEmail(),
                reqVO.getDeptId(), reqVO.getPostIds());
        //插入用户
        AdminUserDO user = UserConvert.INSTANCE.convert( reqVO );
        //默认开启
        user.setStatus(CommonStatusEnum.ENABLE.getStatus() );
        user.setPassword(passwordEncoder.encode(reqVO.getPassword()));
        userMapper.insert(user);
        //插入相关岗位
        if(CollectionUtil.isNotEmpty(reqVO.getPostIds())){
          userPostMapper.insertBatch(convertList(user.getPostIds(),
                  postId -> new UserPostDO().setUserId(user.getId()).setPostId(postId)));
        }
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor =Exception.class)
    public void updateUser(@Valid UserUpdateReqVO reqVO) {
      //校验正确性
      checkCreateOrUpdate(reqVO.getId(), reqVO.getUsername(), reqVO.getMobile(), reqVO.getEmail(),
              reqVO.getDeptId(), reqVO.getPostIds());
      // 更新用户
        AdminUserDO updateObj = UserConvert.INSTANCE.convert(reqVO );
        userMapper.updateById(updateObj);
      //更新岗位
        updateUserPost(reqVO,updateObj);
    }

    /**
     *更新用户岗位
     */
    private void updateUserPost(UserUpdateReqVO reqVO,AdminUserDO updateObj){
        Long userId= reqVO.getId();
        //计算新增和删除的岗位
        Set<Long> dbpostIds = convertSet( userPostMapper.selectListByUserId( userId ), UserPostDO::getUserId );
        Set<Long> postIds = updateObj.getPostIds();
        Collection<Long> createPostIds = CollUtil.subtract( postIds, dbpostIds);
        Collection<Long> deletePostIds = CollUtil.subtract( postIds, dbpostIds);
        // 执行新增和删除。对于已经授权的菜单，不用做任何处理
        if(!CollectionUtil.isEmpty(createPostIds)){
          userPostMapper.insertBatch(convertList(createPostIds,postId->new UserPostDO().setUserId(userId).setPostId(postId)));
        }
        if (!CollectionUtil.isEmpty(deletePostIds)) {
            userPostMapper.deleteByUserIdAndPostId(userId, deletePostIds);
        }
    }

    @Override
    public void updateUserLogin(Long id, String loginIp) {
      userMapper.updateById(new AdminUserDO().setId(id).setLoginIp(loginIp).setLoginDate(new Date()));
    }

    @Override
    public void updateUserProfile(Long id, UserProfileUpdateReqVO reqVO) {
        // 校验正确性
        checkUserExists(id);
        checkEmailUnique(id, reqVO.getEmail());
        checkMobileUnique(id, reqVO.getMobile());
        // 执行更新
        userMapper.updateById(UserConvert.INSTANCE.convert(reqVO).setId(id));
    }

    @Override
    public void updateUserPassword(Long id, @Valid UserProfileUpdatePasswordReqVO reqVO) {
        // 校验旧密码密码
        checkOldPassword(id, reqVO.getOldPassword());
        // 执行更新
        AdminUserDO updateObj = new AdminUserDO().setId(id);
        //加密密码
        updateObj.setPassword(encodePassword(reqVO.getNewPassword()));
        userMapper.updateById(updateObj);
    }

    @Override
    public String updateUserAvatar(Long id, InputStream avatarFile) throws Exception {
        return null;
    }

    @Override
    public void updateUserPassword(Long id, String password) {

    }

    @Override
    public void updateUserStatus(Long id, Integer status) {

    }

    @Override
    public AdminUserDO getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public AdminUserDO getUserByMobile(String mobile) {
        return userMapper.selectByMobile(mobile);
    }

    @Override
    public PageResult<AdminUserDO> getUserPage(UserPageReqVO reqVO) {
        return userMapper.selectPage(reqVO, getDeptCondition(reqVO.getDeptId()));
    }

    @Override
    public AdminUserDO getUser(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<AdminUserDO> getUsersByDeptIds(Collection<Long> deptIds) {
        if (CollUtil.isEmpty(deptIds)) {
            return Collections.emptyList();
        }
        return userMapper.selectListByDeptIds(deptIds);
    }

    @Override
    public List<AdminUserDO> getUsersByPostIds(Collection<Long> postIds) {
        if (CollUtil.isEmpty(postIds)) {
            return Collections.emptyList();
        }
        Set<Long> userIds = convertSet(userPostMapper.selectListByPostIds(postIds), UserPostDO::getUserId);
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(userIds);
    }

    @Override
    public List<AdminUserDO> getUsers(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(ids);
    }

    @Override
    public void validUsers(Set<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得岗位信息
        List<AdminUserDO> users = userMapper.selectBatchIds(ids);
        Map<Long, AdminUserDO> userMap = CollectionUtils.convertMap(users, AdminUserDO::getId);
        // 校验
        ids.forEach(id -> {
            AdminUserDO user = userMap.get(id);
            if (user == null) {
                throw exception(USER_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(user.getStatus())) {
                throw exception(USER_IS_DISABLE, user.getNickname());
            }
        });
    }

    @Override
    public List<AdminUserDO> getUsers(UserExportReqVO reqVO) {
        return userMapper.selectList(reqVO, getDeptCondition(reqVO.getDeptId()));
    }

    @Override
    public List<AdminUserDO> getUsersByNickname(String nickname) {
        return userMapper.selectListByNickname(nickname);
    }

    @Override
    public List<AdminUserDO> getUsersByUsername(String username) {
        return userMapper.selectListByUsername(username);
    }

    /**
     * 获得部门条件：查询指定部门的子部门编号们，包括自身
     * @param deptId 部门编号
     * @return 部门编号集合
     */
    private Set<Long> getDeptCondition(Long deptId) {
        if (deptId == null) {
            return Collections.emptySet();
        }
        Set<Long> deptIds = convertSet(deptService.getDeptsByParentIdFromCache(
                deptId, true), DeptDO::getId);
        deptIds.add(deptId); // 包括自身
        return deptIds;
    }

    private void checkCreateOrUpdate(Long id, String username, String mobile, String email,
                                     Long deptId, Set<Long> postIds) {
        // 校验用户存在
        checkUserExists(id);
        // 校验用户名唯一
        checkUsernameUnique(id, username);
        // 校验手机号唯一
        checkMobileUnique(id, mobile);
        // 校验邮箱唯一
        checkEmailUnique(id, email);
        // 校验部门处于开启状态
        deptService.validDepts(CollectionUtils.singleton(deptId));
    }

    @VisibleForTesting
    public void checkUserExists(Long id) {
        if (id == null) {
            return;
        }
        AdminUserDO user = userMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
    }

    @VisibleForTesting
    public void checkUsernameUnique(Long id, String username) {
        if (StrUtil.isBlank(username)) {
            return;
        }
        AdminUserDO user = userMapper.selectByUsername(username);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_USERNAME_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_USERNAME_EXISTS);
        }
    }

    @VisibleForTesting
    public void checkEmailUnique(Long id, String email) {
        if (StrUtil.isBlank(email)) {
            return;
        }
        AdminUserDO user = userMapper.selectByEmail(email);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_EMAIL_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_EMAIL_EXISTS);
        }
    }

    @VisibleForTesting
    public void checkMobileUnique(Long id, String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return;
        }
        AdminUserDO user = userMapper.selectByMobile(mobile);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_MOBILE_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_MOBILE_EXISTS);
        }
    }

    /**
     * 校验旧密码
     * @param id          用户 id
     * @param oldPassword 旧密码
     */
    @VisibleForTesting
    public void checkOldPassword(Long id, String oldPassword) {
        AdminUserDO user = userMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        if (!isPasswordMatch(oldPassword, user.getPassword())) {
            throw exception(USER_PASSWORD_FAILED);
        }
    }

    @Override
    public List<AdminUserDO> getUsersByStatus(Integer status) {
        return userMapper.selectListByStatus(status);
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 对密码进行加密
     *
     * @param password 密码
     * @return 加密后的密码
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
