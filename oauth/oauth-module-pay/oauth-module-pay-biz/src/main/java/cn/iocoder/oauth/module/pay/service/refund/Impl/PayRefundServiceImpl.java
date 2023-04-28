package cn.iocoder.oauth.module.pay.service.refund.Impl;

import cn.iocoder.oauth.framework.pay.core.client.dto.PayNotifyDataDTO;
import cn.iocoder.oauth.module.pay.dal.dataobject.refund.PayRefundDO;
import cn.iocoder.oauth.module.pay.dal.mysql.refund.PayRefundMapper;
import cn.iocoder.oauth.module.pay.service.order.dto.PayRefundReqDTO;
import cn.iocoder.oauth.module.pay.service.order.dto.PayRefundRespDTO;
import cn.iocoder.oauth.module.pay.service.refund.PayRefundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xiaoxu123
 */
@Service
@Slf4j
public class PayRefundServiceImpl implements PayRefundService {

    @Resource
    private  PayRefundMapper refundMapper;

    @Override
    public PayRefundDO getRefund(Long id) {
        return refundMapper.selectById(id);
    }

    @Override
    public PayRefundRespDTO submitRefundOrder(PayRefundReqDTO reqDTO) {
        return null;
    }

    @Override
    public void notifyPayRefund(Long channelId, PayNotifyDataDTO notifyData) throws Exception {

    }
}
