package com.alibaba.csp.sentinel.dashboard.auth;

import java.lang.annotation.*;

/**
 * @author xiaoxu123
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface AuthAction {

    /**
     *@return the Privilege Type to control
     */
  AuthService.PrivilegeType value();

    /**
     *@return the target name to control
     */
   String targetName() default "app";

    /**
     * @return the message when permission is denied
     */
    String message() default "Permission denied";



}
