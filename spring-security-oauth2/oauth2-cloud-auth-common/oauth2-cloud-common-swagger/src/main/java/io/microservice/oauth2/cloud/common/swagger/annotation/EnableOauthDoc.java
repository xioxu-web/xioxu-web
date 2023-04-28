package io.microservice.oauth2.cloud.common.swagger.annotation;

import io.microservice.oauth2.cloud.common.swagger.config.SwaggerAutoConfiguration;
import io.microservice.oauth2.cloud.common.swagger.support.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xiaoxu123
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Inherited
@EnableConfigurationProperties(SwaggerProperties.class)
@Import({SwaggerAutoConfiguration.class })
public @interface EnableOauthDoc {
}
