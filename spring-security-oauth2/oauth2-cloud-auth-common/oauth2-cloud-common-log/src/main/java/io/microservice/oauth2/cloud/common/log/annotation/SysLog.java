package io.microservice.oauth2.cloud.common.log.annotation;

import java.lang.annotation.*;

/**
 * @author xiaoxu123
 * 操作日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     * 描述
     * @return {String}
     */
    String value() default "";

    /**
     * spel 表达式
     * @return 日志描述
     */
    String expression() default "";


}
