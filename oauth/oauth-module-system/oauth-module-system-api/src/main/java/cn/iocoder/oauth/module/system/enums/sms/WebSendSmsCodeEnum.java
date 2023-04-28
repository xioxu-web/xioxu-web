package cn.iocoder.oauth.module.system.enums.sms;

public class WebSendSmsCodeEnum {

    public enum ResponseCode {
        SUCCESS("0000", "成功"),
        UN_TRADING("0001", "没有该用户账户"),
        ILLEGAL_SECRET("0002", "接口密钥不正确"),
        LACK_QUANTITY("0003", "短信数量不足"),
        ILLEGAL_PARAMETER("0004", "手机号格式不正确");

        private String code;
        private String info;

        ResponseCode(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

    }


}
