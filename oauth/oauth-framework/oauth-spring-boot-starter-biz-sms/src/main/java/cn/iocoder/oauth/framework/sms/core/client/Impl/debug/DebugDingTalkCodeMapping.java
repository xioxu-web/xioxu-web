package cn.iocoder.oauth.framework.sms.core.client.Impl.debug;

import cn.iocoder.oauth.framework.common.exception.ErrorCode;
import cn.iocoder.oauth.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.oauth.framework.sms.core.client.SmsCodeMapping;
import cn.iocoder.oauth.framework.sms.core.enums.SmsFrameworkErrorCodeConstants;

import java.util.Objects;

/**
 * 钉钉的 SmsCodeMapping 实现类
 *
 * @author 芋道源码
 */
public class DebugDingTalkCodeMapping implements SmsCodeMapping {

    @Override
    public ErrorCode apply(String apiCode) {
        return Objects.equals(apiCode, "0") ? GlobalErrorCodeConstants.SUCCESS : SmsFrameworkErrorCodeConstants.SMS_UNKNOWN;
    }

}
