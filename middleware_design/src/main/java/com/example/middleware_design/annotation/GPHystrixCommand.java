package com.example.middleware_design.annotation;

import java.lang.annotation.*;

/**
 * Timeout注解
 * @author xiaoxu123
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface GPHystrixCommand {
   int timeout() default 0;//超时时间

   int semaphore() default 0;//信号量

   String fallback() default "";//回调方法
}
