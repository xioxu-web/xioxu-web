package cn.iocoder.oauth.framework.sms.core.client;

import cn.iocoder.oauth.framework.common.exception.ErrorCode;
import cn.iocoder.oauth.framework.sms.core.enums.SmsFrameworkErrorCodeConstants;


import java.util.function.Function;

/**
 * 将 API 的错误码，转换为通用的错误码
 *
 * @see SmsCommonResult
 * @see SmsFrameworkErrorCodeConstants
 *
 * @author admin
 */
public interface SmsCodeMapping extends Function<String, ErrorCode> {
}
