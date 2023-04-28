package cn.iocoder.oauth.module.pay.service.refund;

import cn.iocoder.oauth.framework.pay.core.client.dto.PayNotifyDataDTO;
import cn.iocoder.oauth.module.pay.dal.dataobject.refund.PayRefundDO;
import cn.iocoder.oauth.module.pay.service.order.dto.PayRefundReqDTO;
import cn.iocoder.oauth.module.pay.service.order.dto.PayRefundRespDTO;

/**
 * 退款订单 Service 接口
 * @author xiaoxu123
 */
public interface PayRefundService {

    /**
     * 获得退款订单
     *
     * @param id 编号
     * @return 退款订单
     */
    PayRefundDO getRefund(Long id);

    /**
     * 提交退款申请
     *
     * @param reqDTO 退款申请信息
     * @return 退款申请返回信息
     */
    PayRefundRespDTO submitRefundOrder(PayRefundReqDTO reqDTO);

    /**
     * 渠道的退款通知
     *
     * @param channelId  渠道编号
     * @param notifyData  通知数据
     * @throws Exception 退款通知异常
     */
    void notifyPayRefund(Long channelId, PayNotifyDataDTO notifyData) throws Exception;
}
