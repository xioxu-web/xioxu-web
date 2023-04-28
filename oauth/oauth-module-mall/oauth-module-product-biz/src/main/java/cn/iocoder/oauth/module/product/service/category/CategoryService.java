package cn.iocoder.oauth.module.product.service.category;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.product.controller.admin.category.vo.CategoryCreateReqVO;
import cn.iocoder.oauth.module.product.controller.admin.category.vo.CategoryPageReqVO;
import cn.iocoder.oauth.module.product.dal.dataobject.category.CategoryDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 商品分类业务接口
 * @author xiaoxu123
 */
public interface CategoryService {

    /**
     *创建商品分类
     */
    Long createCategory(@Valid CategoryCreateReqVO reqVO);

    /**
     * 获得商品分类
     *
     * @param id 编号
     * @return 商品分类
     */
    CategoryDO getCategory(Long id);

    /**
     * 获得商品分类列表
     *
     * @param ids 编号
     * @return 商品分类列表
     */
    List<CategoryDO> getCategoryList(Collection<Long> ids);

    /**
     * 获得商品分类分页
     *
     * @param pageReqVO 分页查询
     * @return 商品分类分页
     */
    PageResult<CategoryDO> getCategoryPage(CategoryPageReqVO pageReqVO);
}
