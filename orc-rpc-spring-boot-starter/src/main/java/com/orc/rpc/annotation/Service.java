package com.orc.rpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author xiaoxu123
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Documented
public @interface Service {

    String value() default "";
}
