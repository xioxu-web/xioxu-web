package cn.iocoder.oauth.module.pay.convert.order;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.pay.core.client.dto.PayOrderUnifiedReqDTO;
import cn.iocoder.oauth.module.pay.controller.admin.order.vo.PayOrderDetailsRespVO;
import cn.iocoder.oauth.module.pay.controller.admin.order.vo.PayOrderPageItemRespVO;
import cn.iocoder.oauth.module.pay.controller.admin.order.vo.PayOrderRespVO;
import cn.iocoder.oauth.module.pay.dal.dataobject.order.PayOrderDO;
import cn.iocoder.oauth.module.pay.dal.dataobject.order.PayOrderExtensionDO;
import cn.iocoder.oauth.module.pay.service.order.dto.PayOrderCreateReqDTO;
import cn.iocoder.oauth.module.pay.service.order.dto.PayOrderSubmitReqDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 支付订单 Convert
 *
 * @author aquan
 */
@Mapper
public interface PayOrderConvert {

    PayOrderConvert INSTANCE = Mappers.getMapper(PayOrderConvert.class);

    PayOrderRespVO convert(PayOrderDO bean);

    PayOrderDetailsRespVO orderDetailConvert(PayOrderDO bean);

    PayOrderDetailsRespVO.PayOrderExtension orderDetailExtensionConvert(PayOrderExtensionDO bean);

    List<PayOrderRespVO> convertList(List<PayOrderDO> list);

    PageResult<PayOrderRespVO> convertPage(PageResult<PayOrderDO> page);


    PayOrderDO convert(PayOrderCreateReqDTO bean);

    PayOrderExtensionDO convert(PayOrderSubmitReqDTO bean);

    PayOrderUnifiedReqDTO convert2(PayOrderSubmitReqDTO bean);

    /**
     * 订单DO转自定义分页对象
     *
     * @param bean 订单DO
     * @return 分页对象
     */
    PayOrderPageItemRespVO pageConvertItemPage(PayOrderDO bean);

}
