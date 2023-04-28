package cn.iocoder.oauth.module.pay.service.order.Impl;

import cn.iocoder.oauth.module.pay.dal.dataobject.order.PayOrderExtensionDO;
import cn.iocoder.oauth.module.pay.dal.mysql.order.PayOrderExtensionMapper;
import cn.iocoder.oauth.module.pay.service.order.PayOrderExtensionService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 支付订单 Service 实现类
 *
 * @author aquan
 */
@Service
@Validated
public class PayOrderExtensionServiceImpl implements PayOrderExtensionService {

    @Resource
    private PayOrderExtensionMapper orderExtensionMapper;

    @Override
    public
    PayOrderExtensionDO getOrderExtension(Long id) {
        return orderExtensionMapper.selectById(id);
    }



}
