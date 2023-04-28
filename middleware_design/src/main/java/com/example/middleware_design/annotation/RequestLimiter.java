package com.example.middleware_design.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoxu123
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimiter {

    /**
     * 每秒创建的令牌个数,默认为10
     */
    double QPS() default 10D;

    /**
     *获取令牌等待超时时间，默认为500
     */
    long timeout() default 500;

    /**
     * 获取令牌时间单位，默认为毫秒
     */
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    /**
     *无法获取令牌提示返回信息
     */
    String msg() default "服务器繁忙,请稍后再试";
}
