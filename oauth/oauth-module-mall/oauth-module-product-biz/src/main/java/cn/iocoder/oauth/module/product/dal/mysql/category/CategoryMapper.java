package cn.iocoder.oauth.module.product.dal.mysql.category;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.oauth.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.oauth.module.product.controller.admin.category.vo.CategoryExportReqVO;
import cn.iocoder.oauth.module.product.controller.admin.category.vo.CategoryPageReqVO;
import cn.iocoder.oauth.module.product.dal.dataobject.category.CategoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品分类 Mapper
 *
 * @author admin
 */
@Mapper
public interface CategoryMapper extends BaseMapperX<CategoryDO> {

    default PageResult<CategoryDO> selectPage(CategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CategoryDO>()
                .likeIfPresent(CategoryDO::getName, reqVO.getName())
                .eqIfPresent(CategoryDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(CategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CategoryDO::getId));
    }

    default List<CategoryDO> selectList(CategoryExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CategoryDO>()
                .likeIfPresent(CategoryDO::getName, reqVO.getName())
                .eqIfPresent(CategoryDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(CategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CategoryDO::getId));
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount( CategoryDO::getParentId, parentId);
    }
}
