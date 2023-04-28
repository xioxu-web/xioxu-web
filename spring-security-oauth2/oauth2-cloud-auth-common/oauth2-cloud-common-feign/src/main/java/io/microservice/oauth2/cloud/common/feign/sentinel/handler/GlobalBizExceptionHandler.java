package io.microservice.oauth2.cloud.common.feign.sentinel.handler;

import com.alibaba.csp.sentinel.Tracer;
import io.microservice.oauth2.common.core.util.ResultMsg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author xiaoxu123
 * 全局异常处理器结合sentinel 全局异常处理器不能作用在 oauth server https://gitee.com/log4j/pig/issues/I1M2TJ
 */
@Slf4j
@Order(1000)
@RestControllerAdvice
@ConditionalOnExpression("!'${security.oauth2.client.clientId}'.isEmpty()")
public class GlobalBizExceptionHandler {

   @ExceptionHandler
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   public ResultMsg handleGlobalException(Exception e){
       log.error("全局异常信息 ex={}", e.getMessage(), e);

       // 业务异常交由 sentinel 记录
       Tracer.trace(e);
       return ResultMsg.failed(e.getLocalizedMessage());
   }

    /**
     * 处理业务校验过程中碰到的非法参数异常 该异常基本由{@link org.springframework.util.Assert}抛出
     * @see Assert#hasLength(String, String)
     * @see Assert#hasText(String, String)
     * @see Assert#isTrue(boolean, String)
     * @see Assert#isNull(Object, String)
     * @see Assert#notNull(Object, String)
     * @param exception 参数校验异常
     * @return API返回结果对象包装后的错误输出结果
     */
   @ExceptionHandler(IllegalArgumentException.class)
   @ResponseStatus(HttpStatus.OK)
   public ResultMsg handleIllegalArgumentException(IllegalArgumentException exception){
     log.error("非法参数异常: ex={}",exception.getMessage(),exception);
     return ResultMsg.ok(exception.getLocalizedMessage());
   }


    /**
     * AccessDeniedException
     * @param exception
     * @retrun ResultMsg
     */
   @ExceptionHandler(AccessDeniedException.class)
   @ResponseStatus(HttpStatus.FORBIDDEN)
   public ResultMsg handleAccessDeniedException(AccessDeniedException exception){
       String msg = SpringSecurityMessageSource.getAccessor().getMessage( "AbstractAccessDecisionManager.accessDenied", exception.getMessage() );
       log.warn("拒绝授权信息: ex={}",msg);
       return ResultMsg.failed(exception.getLocalizedMessage());
   }

    /**
     * validation Exception
     * @param exception
     * @return ResultMsg
     */
   @ExceptionHandler(MethodArgumentNotValidException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ResultMsg handleBodyValidException(MethodArgumentNotValidException exception){
       List<FieldError> fieldError = exception.getBindingResult().getFieldErrors();
       log.error("参数绑定异常: ex={}",fieldError.get(0).getDefaultMessage());
       return ResultMsg.failed(fieldError);
   }

    /**
     * validation Exception (以form-data形式传参)
     * @param exception
     * @return R
     */
    @ExceptionHandler({BindException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultMsg bindExceptionHandler(BindException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        log.warn( "参数绑定异常,ex = {}", fieldErrors.get(0).getDefaultMessage() );
        return ResultMsg.failed( fieldErrors.get( 0 ).getDefaultMessage() );
    }



}
