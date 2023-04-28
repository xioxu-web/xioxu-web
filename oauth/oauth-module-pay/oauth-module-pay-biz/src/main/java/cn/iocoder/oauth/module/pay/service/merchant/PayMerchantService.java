package cn.iocoder.oauth.module.pay.service.merchant;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.common.util.collection.CollectionUtils;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.merchant.PayMerchantCreateReqVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.merchant.PayMerchantPageReqVO;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayMerchantDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 支付商户信息 Service 接口
 *
 * @author aquan
 */
public interface PayMerchantService {

    /**
     * 创建支付商户信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMerchant(@Valid PayMerchantCreateReqVO createReqVO);

    /**
     * 获得支付商户信息
     *
     * @param id 编号
     * @return 支付商户信息
     */
    PayMerchantDO getMerchant(Long id);

    /**
     * 获得支付商户信息列表
     *
     * @param ids 编号
     * @return 支付商户信息列表
     */
    List<PayMerchantDO> getMerchantList(Collection<Long> ids);

    /**
     * 获得支付商户信息分页
     *
     * @param pageReqVO 分页查询
     * @return 支付商户信息分页
     */
    PageResult<PayMerchantDO> getMerchantPage(PayMerchantPageReqVO pageReqVO);

    /**
     * 根据商户名称模糊查询商户集合
     *
     * @param merchantName 商户名称
     * @return 商户集合
     */
    List<PayMerchantDO> getMerchantListByName(String merchantName);

    /**
     * 获得指定编号的商户 Map
     *
     * @param merchantIds 商户编号数组
     * @return 商户 Map
     */
    default Map<Long, PayMerchantDO> getMerchantMap(Collection<Long> merchantIds) {
        List<PayMerchantDO> list =  this.getMerchantList(merchantIds);
        return CollectionUtils.convertMap(list, PayMerchantDO::getId);
    }

}
