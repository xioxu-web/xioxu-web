package cn.iocoder.oauth.module.product.service.brand.Impl;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.product.controller.admin.brand.vo.BrandCreateReqVO;
import cn.iocoder.oauth.module.product.controller.admin.brand.vo.BrandPageReqVO;
import cn.iocoder.oauth.module.product.convert.brand.BrandConvert;
import cn.iocoder.oauth.module.product.dal.dataobject.brand.BrandDO;
import cn.iocoder.oauth.module.product.dal.mysql.brand.BrandMapper;
import cn.iocoder.oauth.module.product.service.brand.BrandService;
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
public class BrandServiceImpl implements BrandService {

    @Resource
    private BrandMapper brandMapper;

    @Override
    public Long createBrand(@Valid BrandCreateReqVO createReqVO) {
        BrandDO brandDO = BrandConvert.INSTANCE.convert( createReqVO );
        brandMapper.insert(brandDO);
        return brandDO.getId();
    }

    @Override
    public BrandDO getBrand(Long id) {
        return brandMapper.selectById(id);
    }

    @Override
    public List<BrandDO> getBrandList(Collection<Long> ids) {
        return brandMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<BrandDO> getBrandPage(BrandPageReqVO pageReqVO) {
        return brandMapper.selectPage(pageReqVO);
    }
}
