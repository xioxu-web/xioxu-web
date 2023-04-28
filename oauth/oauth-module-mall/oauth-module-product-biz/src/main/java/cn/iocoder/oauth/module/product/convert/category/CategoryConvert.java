package cn.iocoder.oauth.module.product.convert.category;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.product.controller.admin.category.vo.CategoryCreateReqVO;
import cn.iocoder.oauth.module.product.controller.admin.category.vo.CategoryRespVO;
import cn.iocoder.oauth.module.product.controller.admin.category.vo.CategoryUpdateReqVO;
import cn.iocoder.oauth.module.product.dal.dataobject.category.CategoryDO;
import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryConvert {

    CategoryConvert INSTANCE = Mappers.getMapper(CategoryConvert.class);

    CategoryDO convert(CategoryCreateReqVO bean);

    CategoryDO convert(CategoryUpdateReqVO bean);

    CategoryRespVO convert(CategoryDO bean);

    List<CategoryRespVO> convertList(List<CategoryDO> list);

    PageResult<CategoryRespVO> convertPage(PageResult<CategoryDO> page);


}
