package cn.iocoder.oauth.module.pay.service.order.Impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.common.util.json.JsonUtils;
import cn.iocoder.oauth.framework.pay.core.client.dto.PayNotifyDataDTO;
import cn.iocoder.oauth.module.pay.controller.admin.order.vo.PayOrderPageReqVO;
import cn.iocoder.oauth.module.pay.convert.order.PayOrderConvert;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayAppDO;
import cn.iocoder.oauth.module.pay.dal.dataobject.order.PayOrderDO;
import cn.iocoder.oauth.module.pay.dal.mysql.order.PayOrderMapper;
import cn.iocoder.oauth.module.pay.enums.order.PayOrderNotifyStatusEnum;
import cn.iocoder.oauth.module.pay.enums.order.PayOrderStatusEnum;
import cn.iocoder.oauth.module.pay.service.merchant.PayAppService;
import cn.iocoder.oauth.module.pay.service.order.PayOrderService;
import cn.iocoder.oauth.module.pay.service.order.dto.PayOrderCreateReqDTO;
import cn.iocoder.oauth.module.pay.service.order.dto.PayOrderSubmitReqDTO;
import cn.iocoder.oauth.module.pay.service.order.dto.PayOrderSubmitRespDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author xiaoxu123
 */
@Service
@Slf4j
public class PayOrderServiceImpl implements PayOrderService {

    @Resource
    private PayOrderMapper orderMapper;

    @Resource
    private PayAppService appService;

    @Override
    public PayOrderDO getOrder(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    public Long createPayOrder(@Valid PayOrderCreateReqDTO reqDTO) {
        // 校验 App
        PayAppDO app = appService.validPayApp(reqDTO.getAppId());

        // 查询对应的支付交易单是否已经存在。如果是，则直接返回
        PayOrderDO order = orderMapper.selectByAppIdAndMerchantOrderId(
                reqDTO.getAppId(), reqDTO.getMerchantOrderId());
        if (order != null) {
            log.warn("[createPayOrder][appId({}) merchantOrderId({}) 已经存在对应的支付单({})]", order.getAppId(),
                    order.getMerchantOrderId(), JsonUtils.toJsonString(order)); // 理论来说，不会出现这个情况
            return app.getId();
        }

        // 创建支付交易单
        order = PayOrderConvert.INSTANCE.convert(reqDTO)
                .setMerchantId(app.getMerchantId()).setAppId(app.getId());
        // 商户相关字段
        order.setNotifyUrl(app.getPayNotifyUrl())
                .setNotifyStatus(PayOrderNotifyStatusEnum.NO.getStatus());
        // 订单相关字段
        order.setStatus(PayOrderStatusEnum.WAITING.getStatus());
        // 退款相关字段
        order.setRefundStatus(PayOrderNotifyStatusEnum.NO.getStatus())
                .setRefundTimes(0).setRefundAmount(0L);
        orderMapper.insert(order);
        // 最终返回
        return order.getId();
    }


    @Override
    public PageResult<PayOrderDO> getOrderPage(PayOrderPageReqVO pageReqVO) {
        return orderMapper.selectPage(pageReqVO);
    }

    private String generateOrderExtensionNo() {
        // 目前的算法
        // 时间序列，年月日时分秒 14 位
        // 纯随机，6 位 TODO 芋艿：此处估计是会有问题的，后续在调整
        return DateUtil.format(new Date(), "yyyyMMddHHmmss") + // 时间序列
                RandomUtil.randomInt(100000, 999999) // 随机。为什么是这个范围，因为偷懒
                ;
    }

    @Override
    public PayOrderSubmitRespDTO submitPayOrder(@Valid PayOrderSubmitReqDTO reqDTO) {
        return null;
    }

    @Override
    public void notifyPayOrder(Long channelId, PayNotifyDataDTO notifyData) throws Exception {

    }
}
