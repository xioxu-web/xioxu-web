package cn.iocoder.oauth.module.pay.dal.mysql.refund;
import cn.iocoder.oauth.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.oauth.module.pay.dal.dataobject.refund.PayRefundDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayRefundMapper extends BaseMapperX<PayRefundDO> {

    default Long selectCount(Long appId, Integer status) {
        return Long.valueOf( selectCount(new LambdaQueryWrapper<PayRefundDO>()
                .eq(PayRefundDO::getAppId, appId)
                .eq(PayRefundDO::getStatus, status)) );
    }

    default PayRefundDO selectByReqNo(String reqNo) {
        return selectOne("req_no", reqNo);
    }

    default  PayRefundDO selectByTradeNoAndMerchantRefundNo(String tradeNo, String merchantRefundNo){
        return selectOne("trade_no", tradeNo, "merchant_refund_no", merchantRefundNo);
    }
}
