package com.example.middleware_design.annotation;

import java.lang.annotation.*;

/**
 * @author xiaoxu123
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface DoWhiteList {

   String key() default "";

   String returnJson() default "";
}
