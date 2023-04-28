package cn.iocoder.oauth.module.pay.convert.order;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.pay.core.client.dto.PayOrderUnifiedReqDTO;
import cn.iocoder.oauth.module.pay.controller.admin.order.vo.PayOrderDetailsRespVO;
import cn.iocoder.oauth.module.pay.controller.admin.order.vo.PayOrderDetailsRespVO.PayOrderExtension;
import cn.iocoder.oauth.module.pay.controller.admin.order.vo.PayOrderPageItemRespVO;
import cn.iocoder.oauth.module.pay.controller.admin.order.vo.PayOrderRespVO;
import cn.iocoder.oauth.module.pay.dal.dataobject.order.PayOrderDO;
import cn.iocoder.oauth.module.pay.dal.dataobject.order.PayOrderExtensionDO;
import cn.iocoder.oauth.module.pay.service.order.dto.PayOrderCreateReqDTO;
import cn.iocoder.oauth.module.pay.service.order.dto.PayOrderSubmitReqDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-25T14:54:45+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class PayOrderConvertImpl implements PayOrderConvert {

    @Override
    public PayOrderRespVO convert(PayOrderDO bean) {
        if ( bean == null ) {
            return null;
        }

        PayOrderRespVO payOrderRespVO = new PayOrderRespVO();

        payOrderRespVO.setMerchantId( bean.getMerchantId() );
        payOrderRespVO.setAppId( bean.getAppId() );
        payOrderRespVO.setChannelId( bean.getChannelId() );
        payOrderRespVO.setChannelCode( bean.getChannelCode() );
        payOrderRespVO.setMerchantOrderId( bean.getMerchantOrderId() );
        payOrderRespVO.setSubject( bean.getSubject() );
        payOrderRespVO.setBody( bean.getBody() );
        payOrderRespVO.setNotifyUrl( bean.getNotifyUrl() );
        payOrderRespVO.setNotifyStatus( bean.getNotifyStatus() );
        payOrderRespVO.setAmount( bean.getAmount() );
        payOrderRespVO.setChannelFeeRate( bean.getChannelFeeRate() );
        payOrderRespVO.setChannelFeeAmount( bean.getChannelFeeAmount() );
        payOrderRespVO.setStatus( bean.getStatus() );
        payOrderRespVO.setUserIp( bean.getUserIp() );
        payOrderRespVO.setExpireTime( bean.getExpireTime() );
        payOrderRespVO.setSuccessTime( bean.getSuccessTime() );
        payOrderRespVO.setNotifyTime( bean.getNotifyTime() );
        payOrderRespVO.setSuccessExtensionId( bean.getSuccessExtensionId() );
        payOrderRespVO.setRefundStatus( bean.getRefundStatus() );
        payOrderRespVO.setRefundTimes( bean.getRefundTimes() );
        payOrderRespVO.setRefundAmount( bean.getRefundAmount() );
        payOrderRespVO.setChannelUserId( bean.getChannelUserId() );
        payOrderRespVO.setChannelOrderNo( bean.getChannelOrderNo() );
        payOrderRespVO.setId( bean.getId() );
        payOrderRespVO.setCreateTime( bean.getCreateTime() );

        return payOrderRespVO;
    }

    @Override
    public PayOrderDetailsRespVO orderDetailConvert(PayOrderDO bean) {
        if ( bean == null ) {
            return null;
        }

        PayOrderDetailsRespVO payOrderDetailsRespVO = new PayOrderDetailsRespVO();

        payOrderDetailsRespVO.setMerchantId( bean.getMerchantId() );
        payOrderDetailsRespVO.setAppId( bean.getAppId() );
        payOrderDetailsRespVO.setChannelId( bean.getChannelId() );
        payOrderDetailsRespVO.setChannelCode( bean.getChannelCode() );
        payOrderDetailsRespVO.setMerchantOrderId( bean.getMerchantOrderId() );
        payOrderDetailsRespVO.setSubject( bean.getSubject() );
        payOrderDetailsRespVO.setBody( bean.getBody() );
        payOrderDetailsRespVO.setNotifyUrl( bean.getNotifyUrl() );
        payOrderDetailsRespVO.setNotifyStatus( bean.getNotifyStatus() );
        payOrderDetailsRespVO.setAmount( bean.getAmount() );
        payOrderDetailsRespVO.setChannelFeeRate( bean.getChannelFeeRate() );
        payOrderDetailsRespVO.setChannelFeeAmount( bean.getChannelFeeAmount() );
        payOrderDetailsRespVO.setStatus( bean.getStatus() );
        payOrderDetailsRespVO.setUserIp( bean.getUserIp() );
        payOrderDetailsRespVO.setExpireTime( bean.getExpireTime() );
        payOrderDetailsRespVO.setSuccessTime( bean.getSuccessTime() );
        payOrderDetailsRespVO.setNotifyTime( bean.getNotifyTime() );
        payOrderDetailsRespVO.setSuccessExtensionId( bean.getSuccessExtensionId() );
        payOrderDetailsRespVO.setRefundStatus( bean.getRefundStatus() );
        payOrderDetailsRespVO.setRefundTimes( bean.getRefundTimes() );
        payOrderDetailsRespVO.setRefundAmount( bean.getRefundAmount() );
        payOrderDetailsRespVO.setChannelUserId( bean.getChannelUserId() );
        payOrderDetailsRespVO.setChannelOrderNo( bean.getChannelOrderNo() );
        payOrderDetailsRespVO.setId( bean.getId() );
        payOrderDetailsRespVO.setCreateTime( bean.getCreateTime() );

        return payOrderDetailsRespVO;
    }

    @Override
    public PayOrderExtension orderDetailExtensionConvert(PayOrderExtensionDO bean) {
        if ( bean == null ) {
            return null;
        }

        PayOrderExtension payOrderExtension = new PayOrderExtension();

        payOrderExtension.setNo( bean.getNo() );
        payOrderExtension.setChannelNotifyData( bean.getChannelNotifyData() );

        return payOrderExtension;
    }

    @Override
    public List<PayOrderRespVO> convertList(List<PayOrderDO> list) {
        if ( list == null ) {
            return null;
        }

        List<PayOrderRespVO> list1 = new ArrayList<PayOrderRespVO>( list.size() );
        for ( PayOrderDO payOrderDO : list ) {
            list1.add( convert( payOrderDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<PayOrderRespVO> convertPage(PageResult<PayOrderDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<PayOrderRespVO> pageResult = new PageResult<PayOrderRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );

        return pageResult;
    }

    @Override
    public PayOrderDO convert(PayOrderCreateReqDTO bean) {
        if ( bean == null ) {
            return null;
        }

        PayOrderDO payOrderDO = new PayOrderDO();

        payOrderDO.setAppId( bean.getAppId() );
        payOrderDO.setMerchantOrderId( bean.getMerchantOrderId() );
        payOrderDO.setSubject( bean.getSubject() );
        payOrderDO.setBody( bean.getBody() );
        if ( bean.getAmount() != null ) {
            payOrderDO.setAmount( bean.getAmount().longValue() );
        }
        payOrderDO.setUserIp( bean.getUserIp() );
        payOrderDO.setExpireTime( bean.getExpireTime() );

        return payOrderDO;
    }

    @Override
    public PayOrderExtensionDO convert(PayOrderSubmitReqDTO bean) {
        if ( bean == null ) {
            return null;
        }

        PayOrderExtensionDO payOrderExtensionDO = new PayOrderExtensionDO();

        payOrderExtensionDO.setId( bean.getId() );
        payOrderExtensionDO.setChannelCode( bean.getChannelCode() );
        payOrderExtensionDO.setUserIp( bean.getUserIp() );
        Map<String, String> map = bean.getChannelExtras();
        if ( map != null ) {
            payOrderExtensionDO.setChannelExtras( new HashMap<String, String>( map ) );
        }

        return payOrderExtensionDO;
    }

    @Override
    public PayOrderUnifiedReqDTO convert2(PayOrderSubmitReqDTO bean) {
        if ( bean == null ) {
            return null;
        }

        PayOrderUnifiedReqDTO payOrderUnifiedReqDTO = new PayOrderUnifiedReqDTO();

        payOrderUnifiedReqDTO.setUserIp( bean.getUserIp() );
        Map<String, String> map = bean.getChannelExtras();
        if ( map != null ) {
            payOrderUnifiedReqDTO.setChannelExtras( new HashMap<String, String>( map ) );
        }

        return payOrderUnifiedReqDTO;
    }

    @Override
    public PayOrderPageItemRespVO pageConvertItemPage(PayOrderDO bean) {
        if ( bean == null ) {
            return null;
        }

        PayOrderPageItemRespVO payOrderPageItemRespVO = new PayOrderPageItemRespVO();

        payOrderPageItemRespVO.setMerchantId( bean.getMerchantId() );
        payOrderPageItemRespVO.setAppId( bean.getAppId() );
        payOrderPageItemRespVO.setChannelId( bean.getChannelId() );
        payOrderPageItemRespVO.setChannelCode( bean.getChannelCode() );
        payOrderPageItemRespVO.setMerchantOrderId( bean.getMerchantOrderId() );
        payOrderPageItemRespVO.setSubject( bean.getSubject() );
        payOrderPageItemRespVO.setBody( bean.getBody() );
        payOrderPageItemRespVO.setNotifyUrl( bean.getNotifyUrl() );
        payOrderPageItemRespVO.setNotifyStatus( bean.getNotifyStatus() );
        payOrderPageItemRespVO.setAmount( bean.getAmount() );
        payOrderPageItemRespVO.setChannelFeeRate( bean.getChannelFeeRate() );
        payOrderPageItemRespVO.setChannelFeeAmount( bean.getChannelFeeAmount() );
        payOrderPageItemRespVO.setStatus( bean.getStatus() );
        payOrderPageItemRespVO.setUserIp( bean.getUserIp() );
        payOrderPageItemRespVO.setExpireTime( bean.getExpireTime() );
        payOrderPageItemRespVO.setSuccessTime( bean.getSuccessTime() );
        payOrderPageItemRespVO.setNotifyTime( bean.getNotifyTime() );
        payOrderPageItemRespVO.setSuccessExtensionId( bean.getSuccessExtensionId() );
        payOrderPageItemRespVO.setRefundStatus( bean.getRefundStatus() );
        payOrderPageItemRespVO.setRefundTimes( bean.getRefundTimes() );
        payOrderPageItemRespVO.setRefundAmount( bean.getRefundAmount() );
        payOrderPageItemRespVO.setChannelUserId( bean.getChannelUserId() );
        payOrderPageItemRespVO.setChannelOrderNo( bean.getChannelOrderNo() );
        payOrderPageItemRespVO.setId( bean.getId() );
        payOrderPageItemRespVO.setCreateTime( bean.getCreateTime() );

        return payOrderPageItemRespVO;
    }
}
