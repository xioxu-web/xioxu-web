package cn.iocoder.oauth.module.system.dal.mysql.dept;

import cn.iocoder.oauth.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.oauth.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.dept.DeptDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author xiaoxu123
 */
@Mapper
public interface DeptMapper extends BaseMapperX<DeptDO> {

    default List<DeptDO> selectList(DeptListReqVO reqVO){
      return selectList(new LambdaQueryWrapperX<DeptDO>()
             .likeIfPresent(DeptDO::getName, reqVO.getName() )
             .eqIfPresent(DeptDO::getStatus,reqVO.getStatus()));
    }

    default DeptDO selectByParentIdAndName(Long parentId, String name) {
        return selectOne(new LambdaQueryWrapper<DeptDO>()
                .eq(DeptDO::getParentId, parentId)
                .eq(DeptDO::getName, name));
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(DeptDO::getParentId, parentId);
    }

    @Select("SELECT COUNT(*) FROM system_dept WHERE update_time > #{maxUpdateTime}")
    Long selectCountByUpdateTimeGt(Date maxUpdateTime);

}
