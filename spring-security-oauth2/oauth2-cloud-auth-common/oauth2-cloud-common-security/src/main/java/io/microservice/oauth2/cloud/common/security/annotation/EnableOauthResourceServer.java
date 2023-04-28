package io.microservice.oauth2.cloud.common.security.annotation;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.lang.annotation.*;

/**
 * @author xiaoxu123
 * 资源服务注解
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public @interface EnableOauthResourceServer {


}
