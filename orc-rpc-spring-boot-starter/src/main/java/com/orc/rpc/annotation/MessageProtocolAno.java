package com.orc.rpc.annotation;

import java.lang.annotation.*;

/**
 * @author xiaoxu123
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageProtocolAno {

    String value() default "";
}
