package cn.iocoder.oauth.module.system.service.dept;
import cn.hutool.core.collection.CollUtil;
import cn.iocoder.oauth.framework.common.util.collection.CollectionUtils;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.dept.DeptCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.dept.DeptUpdateReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.dept.DeptDO;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * 部门 Service 接口
 *
 * @author admin
 */
public interface DeptService {

    /**
     * 初始化部门的本地缓存
     */
    void initLocalCache();

    /**
     * 筛选部门列表
     *
     * @param reqVO 筛选条件请求 VO
     * @return 部门列表
     */
    List<DeptDO> getSimpleDepts(DeptListReqVO reqVO);

    /**
     * 获得所有子部门，从缓存中
     *
     * @param parentId 部门编号
     * @param recursive 是否递归获取所有
     * @return 子部门列表
     */
    List<DeptDO> getDeptsByParentIdFromCache(Long parentId, boolean recursive);

    /**
     * 获得部门信息数组
     *
     * @param ids 部门编号数组
     * @return 部门信息数组
     */
    List<DeptDO> getDepts(Collection<Long> ids);

    /**
     * 获得部门信息
     *
     * @param id 部门编号
     * @return 部门信息
     */
    DeptDO getDept(Long id);

    /**
     * 校验部门们是否有效。如下情况，视为无效：
     * 1. 部门编号不存在
     * 2. 部门被禁用
     *
     * @param ids 角色编号数组
     */
    void validDepts(Collection<Long> ids);

    /**
     * 获得指定编号的部门列表
     *
     * @param ids 部门编号数组
     * @return 部门列表
     */
    List<DeptDO> getSimpleDepts(Collection<Long> ids);

    /**
     * 获得指定编号的部门 Map
     *
     * @param ids 部门编号数组
     * @return 部门 Map
     */
    default Map<Long, DeptDO> getDeptMap(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        List<DeptDO> list = getSimpleDepts(ids);
        return CollectionUtils.convertMap(list, DeptDO::getId);
    }
}
