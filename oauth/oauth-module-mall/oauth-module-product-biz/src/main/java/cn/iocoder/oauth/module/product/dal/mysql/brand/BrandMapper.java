package cn.iocoder.oauth.module.product.dal.mysql.brand;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.oauth.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.oauth.module.product.controller.admin.brand.vo.BrandPageReqVO;
import cn.iocoder.oauth.module.product.dal.dataobject.brand.BrandDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 品牌 Mapper
 *
 * @author admin
 */
@Mapper
public interface BrandMapper extends BaseMapperX<BrandDO> {

    default PageResult<BrandDO> selectPage(BrandPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BrandDO>()
                .eqIfPresent(BrandDO::getCategoryId, reqVO.getCategoryId())
                .likeIfPresent(BrandDO::getName, reqVO.getName())
                .eqIfPresent(BrandDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(BrandDO::getCreateTime,reqVO.getCreateTime())
                .orderByDesc(BrandDO::getId));
    }



}
