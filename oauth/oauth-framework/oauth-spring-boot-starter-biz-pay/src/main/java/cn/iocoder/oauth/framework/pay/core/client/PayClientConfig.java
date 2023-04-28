package cn.iocoder.oauth.framework.pay.core.client;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * 支付客户端的配置，本质是支付渠道的配置
 * @author xiaoxu123
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface PayClientConfig {

    /**
     * 配置验证参数是
     *
     * @param validator 校验对象
     * @return 配置好的验证参数
     */
    Set<ConstraintViolation<PayClientConfig>> verifyParam(Validator validator);


    /**
     * 参数校验
     *
     * @param validator 校验对象
     */
    default void validate(Validator validator) {
        Set<ConstraintViolation<PayClientConfig>> violations = verifyParam(validator);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
