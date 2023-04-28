package cn.iocoder.oauth.framework.pay.core.client;
import cn.iocoder.oauth.framework.common.exception.ErrorCode;
import cn.iocoder.oauth.framework.pay.core.enums.PayFrameworkErrorCodeConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * 将 API 的错误码，转换为通用的错误码
 *
 * @see PayCommonResult
 * @see PayFrameworkErrorCodeConstants
 *
 * @author admin
 */
@Slf4j
public abstract class AbstractPayCodeMapping {

    public final ErrorCode apply(String apiCode, String apiMsg) {
        if (apiCode == null) {
            log.error("[apply][API 错误码为空，请排查]");
            return PayFrameworkErrorCodeConstants.EXCEPTION;
        }
        ErrorCode errorCode = this.apply0(apiCode, apiMsg);
        if (errorCode == null) {
            log.error("[apply][API 错误码({}) 错误提示({}) 无法匹配]", apiCode, apiMsg);
            return PayFrameworkErrorCodeConstants.PAY_UNKNOWN;
        }
        return errorCode;
    }

    protected abstract ErrorCode apply0(String apiCode, String apiMsg);

}
