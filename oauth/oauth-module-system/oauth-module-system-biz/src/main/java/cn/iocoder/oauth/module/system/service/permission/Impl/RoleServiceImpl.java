package cn.iocoder.oauth.module.system.service.permission.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.oauth.framework.common.enums.CommonStatusEnum;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.common.util.collection.CollectionUtils;
import cn.iocoder.oauth.module.system.controller.admin.permission.vo.role.RoleCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.permission.vo.role.RoleExportReqVO;
import cn.iocoder.oauth.module.system.controller.admin.permission.vo.role.RolePageReqVO;
import cn.iocoder.oauth.module.system.controller.admin.permission.vo.role.RoleUpdateReqVO;
import cn.iocoder.oauth.module.system.convert.permission.RoleConvert;
import cn.iocoder.oauth.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.oauth.module.system.dal.mysql.permission.RoleMapper;
import cn.iocoder.oauth.module.system.enums.permission.DataScopeEnum;
import cn.iocoder.oauth.module.system.enums.permission.RoleCodeEnum;
import cn.iocoder.oauth.module.system.enums.permission.RoleTypeEnum;
import cn.iocoder.oauth.module.system.service.permission.RoleService;
import com.google.common.annotations.VisibleForTesting;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.oauth.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.oauth.module.system.enums.ErrorCodeConstants.*;

/**
 * @author xiaoxu123
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger log= LoggerFactory.getLogger(RoleServiceImpl.class);

    /**
     * 角色缓存
     */
    @Getter
    private volatile Map<Long, RoleDO> roleCache;

    /**
     * 缓存角色的最大更新时间，用于后续的增量轮询，判断是否有更新
     */
    @Getter
    private volatile Date maxUpdateTime;


    @Resource
    private RoleMapper roleMapper;


    @Resource
    @Lazy // 注入自己，所以延迟加载
    private RoleService self;


    /**
     * 初始化 {@link #roleCache} 缓存
     */
    @Override
    @PostConstruct
    public void initLocalCache() {
        // 获取角色列表，如果有更新
        List<RoleDO> roleList = loadRoleIfUpdate(maxUpdateTime);
        if (CollUtil.isEmpty(roleList)) {
            return;
        }
        // 写入缓存
        roleCache = CollectionUtils.convertMap(roleList, RoleDO::getId);
        maxUpdateTime = CollectionUtils.getMaxValue(roleList, RoleDO::getUpdateTime);
        log.info("[initLocalCache][初始化 Role 数量为 {}]", roleList.size());
    }

    /**
     * 如果角色发生变化，从数据库中获取最新的全量角色。
     * 如果未发生变化，则返回空
     *
     * @param maxUpdateTime 当前角色的最大更新时间
     * @return 角色列表
     */
    private List<RoleDO> loadRoleIfUpdate(Date maxUpdateTime) {
        // 第一步，判断是否要更新。
        if (maxUpdateTime == null) { // 如果更新时间为空，说明 DB 一定有新数据
            log.info("[loadRoleIfUpdate][首次加载全量角色]");
        } else { // 判断数据库中是否有更新的角色
            if (roleMapper.selectCountByUpdateTimeGt(maxUpdateTime) == 0) {
                return null;
            }
            log.info("[loadRoleIfUpdate][增量加载全量角色]");
        }
        // 第二步，如果有更新，则从数据库加载所有角色
        return roleMapper.selectList();
    }

    @Override
    @Transactional
    public Long createRole(RoleCreateReqVO reqVO, Integer type) {
        // 校验角色
        checkDuplicateRole(reqVO.getName(), reqVO.getCode(), null);
        // 插入到数据库
        RoleDO role = RoleConvert.INSTANCE.convert(reqVO);
        role.setType( ObjectUtil.defaultIfNull(type, RoleTypeEnum.CUSTOM.getType()));
        role.setStatus( CommonStatusEnum.ENABLE.getStatus());
        role.setDataScope(DataScopeEnum.ALL.getScope()); // 默认可查看所有数据。原因是，可能一些项目不需要项目权限
        roleMapper.insert(role);
        // 返回
        return role.getId();
    }

    @Override
    public void updateRole(RoleUpdateReqVO reqVO) {
        // 校验是否可以更新
        checkUpdateRole(reqVO.getId());
        // 校验角色的唯一字段是否重复
        checkDuplicateRole(reqVO.getName(), reqVO.getCode(), reqVO.getId());

        // 更新到数据库
        RoleDO updateObject = RoleConvert.INSTANCE.convert(reqVO);
        roleMapper.updateById(updateObject);
    }

    @Override
    public void updateRoleStatus(Long id, Integer status) {
        // 校验是否可以更新
        checkUpdateRole(id);
        // 更新状态
        RoleDO updateObject = new RoleDO();
        updateObject.setId(id);
        updateObject.setStatus(status);
        roleMapper.updateById(updateObject);
    }

    @Override
    public void updateRoleDataScope(Long id, Integer dataScope, Set<Long> dataScopeDeptIds) {
        // 校验是否可以更新
        checkUpdateRole(id);
        // 更新数据范围
        RoleDO updateObject = new RoleDO();
        updateObject.setId(id);
        updateObject.setDataScope(dataScope);
        updateObject.setDataScopeDeptIds(dataScopeDeptIds);
        roleMapper.updateById(updateObject);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id) {
        // 校验是否可以更新
        this.checkUpdateRole(id);
        // 标记删除
        roleMapper.deleteById(id);
    }

    @Override
    public RoleDO getRoleFromCache(Long id) {
        return roleCache.get(id);
    }

    @Override
    public List<RoleDO> getRoles(@Nullable Collection<Integer> statuses) {
        if (CollUtil.isEmpty(statuses)) {
            return roleMapper.selectList();
        }
        return roleMapper.selectListByStatus(statuses);
    }

    @Override
    public List<RoleDO> getRolesFromCache(Collection<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return roleCache.values().stream().filter(roleDO -> ids.contains(roleDO.getId()))
                .collect( Collectors.toList());
    }

    @Override
    public boolean hasAnySuperAdmin(Collection<RoleDO> roleList) {
        if (CollectionUtil.isEmpty(roleList)) {
            return false;
        }
        return roleList.stream().anyMatch(role -> RoleCodeEnum.isSuperAdmin(role.getCode()));
    }

    @Override
    public RoleDO getRole(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public PageResult<RoleDO> getRolePage(RolePageReqVO reqVO) {
        return roleMapper.selectPage(reqVO);
    }

    @Override
    public List<RoleDO> getRoleList(RoleExportReqVO reqVO) {
        return roleMapper.selectList(reqVO);
    }

    /**
     * 校验角色的唯一字段是否重复
     *
     * 1. 是否存在相同名字的角色
     * 2. 是否存在相同编码的角色
     *
     * @param name 角色名字
     * @param code 角色额编码
     * @param id 角色编号
     */
    @VisibleForTesting
    public void checkDuplicateRole(String name, String code, Long id) {
        // 0. 超级管理员，不允许创建
        if (RoleCodeEnum.isSuperAdmin(code)) {
            throw exception(ROLE_ADMIN_CODE_ERROR, code);
        }
        // 1. 该 name 名字被其它角色所使用
        RoleDO role = roleMapper.selectByName(name);
        if (role != null && !role.getId().equals(id)) {
            throw exception(ROLE_NAME_DUPLICATE, name);
        }
        // 2. 是否存在相同编码的角色
        if (!StringUtils.hasText(code)) {
            return;
        }
        // 该 code 编码被其它角色所使用
        role = roleMapper.selectByCode(code);
        if (role != null && !role.getId().equals(id)) {
            throw exception(ROLE_CODE_DUPLICATE, code);
        }
    }

    /**
     * 校验角色是否可以被更新
     *
     * @param id 角色编号
     */
    @VisibleForTesting
    public void checkUpdateRole(Long id) {
        RoleDO roleDO = roleMapper.selectById(id);
        if (roleDO == null) {
            throw exception(ROLE_NOT_EXISTS);
        }
        // 内置角色，不允许删除
        if (RoleTypeEnum.SYSTEM.getType().equals(roleDO.getType())) {
            throw exception(ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE);
        }
    }

    @Override
    public void validRoles(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得角色信息
        List<RoleDO> roles = roleMapper.selectBatchIds(ids);
        Map<Long, RoleDO> roleMap = CollectionUtils.convertMap(roles, RoleDO::getId);
        // 校验
        ids.forEach(id -> {
            RoleDO role = roleMap.get(id);
            if (role == null) {
                throw exception(ROLE_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(role.getStatus())) {
                throw exception(ROLE_IS_DISABLE, role.getName());
            }
        });
    }
}
