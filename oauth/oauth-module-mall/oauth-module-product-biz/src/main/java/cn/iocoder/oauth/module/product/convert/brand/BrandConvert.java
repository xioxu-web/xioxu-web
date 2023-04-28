package cn.iocoder.oauth.module.product.convert.brand;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.product.controller.admin.brand.vo.BrandCreateReqVO;
import cn.iocoder.oauth.module.product.controller.admin.brand.vo.BrandRespVO;
import cn.iocoder.oauth.module.product.dal.dataobject.brand.BrandDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 品牌 Convert
 *
 * @author admin
 */
@Mapper
public interface BrandConvert {

    BrandConvert INSTANCE = Mappers.getMapper(BrandConvert.class);

    BrandDO convert(BrandCreateReqVO bean);


    BrandRespVO convert(BrandDO bean);

    List<BrandRespVO> convertList(List<BrandDO> list);

    PageResult<BrandRespVO> convertPage(PageResult<BrandDO> page);

}
