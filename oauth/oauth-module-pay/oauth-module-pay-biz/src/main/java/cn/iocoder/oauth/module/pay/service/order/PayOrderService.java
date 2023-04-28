package cn.iocoder.oauth.module.pay.service.order;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.pay.core.client.dto.PayNotifyDataDTO;
import cn.iocoder.oauth.module.pay.controller.admin.order.vo.PayOrderPageReqVO;
import cn.iocoder.oauth.module.pay.dal.dataobject.order.PayOrderDO;
import cn.iocoder.oauth.module.pay.service.order.dto.PayOrderCreateReqDTO;
import cn.iocoder.oauth.module.pay.service.order.dto.PayOrderSubmitReqDTO;
import cn.iocoder.oauth.module.pay.service.order.dto.PayOrderSubmitRespDTO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 支付订单 Service 接口
 *
 * @author aquan
 */
public interface PayOrderService {

    /**
     * 获得支付订单
     *
     * @param id 编号
     * @return 支付订单
     */
    PayOrderDO getOrder(Long id);


    /**
     * 创建支付单
     *
     * @param reqDTO 创建请求
     * @return 支付单编号
     */
    Long createPayOrder(@Valid PayOrderCreateReqDTO reqDTO);

    /**
     * 获得支付订单
     * 分页
     *
     * @param pageReqVO 分页查询
     * @return 支付订单
     * 分页
     */
    PageResult<PayOrderDO> getOrderPage(PayOrderPageReqVO pageReqVO);

    /**
     * 提交支付
     * 此时，会发起支付渠道的调用
     *
     * @param reqDTO 提交请求
     * @return 提交结果
     */
    PayOrderSubmitRespDTO submitPayOrder(@Valid PayOrderSubmitReqDTO reqDTO);

    /**
     * 通知支付单成功
     *
     * @param channelId 渠道编号
     * @param notifyData 通知数据
     */
    void notifyPayOrder(Long channelId,  PayNotifyDataDTO notifyData) throws Exception;

}
