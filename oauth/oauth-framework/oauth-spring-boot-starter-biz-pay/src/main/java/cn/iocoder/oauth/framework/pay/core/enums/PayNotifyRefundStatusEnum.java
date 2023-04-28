package cn.iocoder.oauth.framework.pay.core.enums;

public enum PayNotifyRefundStatusEnum {

    /**
     * 支付宝 中 全额退款 trade_status=TRADE_CLOSED， 部分退款 trade_status=TRADE_SUCCESS
     * 退款成功
     */
    SUCCESS,

    /**
     * 支付宝退款通知没有这个状态
     * 退款异常
     */
    ABNORMAL;
}
