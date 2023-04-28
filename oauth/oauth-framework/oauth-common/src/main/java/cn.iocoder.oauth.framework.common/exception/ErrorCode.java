package cn.iocoder.oauth.framework.common.exception;
import cn.iocoder.oauth.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.oauth.framework.common.exception.enums.ServiceErrorCodeRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 错误码对象
 *
 * 全局错误码，占用 [0, 999], 参见 {@link GlobalErrorCodeConstants}
 * 业务异常错误码，占用 [1 000 000 000, +∞)，参见 {@link ServiceErrorCodeRange}
 *
 * TODO 错误码设计成对象的原因，为未来的 i18 国际化做准备
 * @author xiaoxu123
 */
@Data
@Accessors(chain = true)
@Builder
public class ErrorCode {

    /**
     * 错误码
     */
    private final Integer code;
    /**
     * 错误提示
     */
    private final String msg;

    public ErrorCode(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

}
