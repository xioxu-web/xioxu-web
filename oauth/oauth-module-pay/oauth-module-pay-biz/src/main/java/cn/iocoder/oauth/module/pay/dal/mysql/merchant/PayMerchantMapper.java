package cn.iocoder.oauth.module.pay.dal.mysql.merchant;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.oauth.framework.mybatis.core.query.QueryWrapperX;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.merchant.PayMerchantPageReqVO;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayMerchantDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * @author xiaoxu123
 */
@Mapper
public interface PayMerchantMapper extends BaseMapperX<PayMerchantDO> {

    default
    PageResult<PayMerchantDO> selectPage(PayMerchantPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<PayMerchantDO>()
                .likeIfPresent("no", reqVO.getNo())
                .likeIfPresent("name", reqVO.getName())
                .likeIfPresent("short_name", reqVO.getShortName())
                .eqIfPresent("status", reqVO.getStatus())
                .eqIfPresent("remark", reqVO.getRemark())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id"));
    }

    /**
     * 根据商户名称模糊查询商户集合
     *
     * @param merchantName 商户名称
     * @return 商户集合
     */
    default List<PayMerchantDO> getMerchantListByName(String merchantName) {
        return this.selectList(new QueryWrapperX<PayMerchantDO>()
                .likeIfPresent("name", merchantName));
    }
}
