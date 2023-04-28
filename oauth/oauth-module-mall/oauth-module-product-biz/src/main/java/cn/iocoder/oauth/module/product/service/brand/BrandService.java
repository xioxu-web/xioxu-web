package cn.iocoder.oauth.module.product.service.brand;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.product.controller.admin.brand.vo.BrandCreateReqVO;
import cn.iocoder.oauth.module.product.controller.admin.brand.vo.BrandPageReqVO;
import cn.iocoder.oauth.module.product.dal.dataobject.brand.BrandDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 品牌业务接口
 * @author xiaoxu123
 */
public interface BrandService {

    /**
     * 创建品牌
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBrand(@Valid BrandCreateReqVO createReqVO);

    /**
     * 获得品牌
     *
     * @param id 编号
     * @return 品牌
     */
    BrandDO getBrand(Long id);

    /**
     * 获得品牌列表
     *
     * @param ids 编号
     * @return 品牌列表
     */
    List<BrandDO> getBrandList(Collection<Long> ids);

    /**
     * 获得品牌分页
     *
     * @param pageReqVO 分页查询
     * @return 品牌分页
     */
    PageResult<BrandDO> getBrandPage(BrandPageReqVO pageReqVO);
}
