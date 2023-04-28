package cn.iocoder.oauth.module.pay.service.order;

import cn.iocoder.oauth.module.pay.dal.dataobject.order.PayOrderExtensionDO;

/**
 * 支付订单 Service 接口
 *
 * @author aquan
 */
public interface PayOrderExtensionService {

    /**
     * 获得支付订单
     *
     * @param id 编号
     * @return 支付订单
     */
    PayOrderExtensionDO getOrderExtension(Long id);




}
