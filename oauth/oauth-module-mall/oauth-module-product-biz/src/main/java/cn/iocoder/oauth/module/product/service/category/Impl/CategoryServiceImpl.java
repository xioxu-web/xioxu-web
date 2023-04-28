package cn.iocoder.oauth.module.product.service.category.Impl;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.product.controller.admin.category.vo.CategoryCreateReqVO;
import cn.iocoder.oauth.module.product.controller.admin.category.vo.CategoryPageReqVO;
import cn.iocoder.oauth.module.product.convert.category.CategoryConvert;
import cn.iocoder.oauth.module.product.dal.dataobject.category.CategoryDO;
import cn.iocoder.oauth.module.product.dal.mysql.category.CategoryMapper;
import cn.iocoder.oauth.module.product.service.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * @author xiaoxu123
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Long createCategory(@Valid CategoryCreateReqVO reqVO) {
        CategoryDO categoryDO = CategoryConvert.INSTANCE.convert( reqVO );
        categoryMapper.insert(categoryDO);
        return categoryDO.getId();
    }

    @Override
    public CategoryDO getCategory(Long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public List<CategoryDO> getCategoryList(Collection<Long> ids) {
        return categoryMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<CategoryDO> getCategoryPage(CategoryPageReqVO pageReqVO) {
        return categoryMapper.selectPage( pageReqVO);
    }
}
