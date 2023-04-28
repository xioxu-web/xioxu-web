package com.example.middleware_design.annotation;

import java.lang.annotation.*;

/**
 * @author xiaoxu123
 */ /*
* 作用在方法上,在运行时通过反射获取信息
*
* */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DoRateLimiter {
    double permitsPerSecond() default 0D;   // 限流许可量
    long timeout();         // 失败结果 JSON
}
