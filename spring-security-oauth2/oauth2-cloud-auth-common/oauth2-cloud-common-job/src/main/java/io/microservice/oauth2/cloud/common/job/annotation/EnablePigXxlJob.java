package io.microservice.oauth2.cloud.common.job.annotation;

import java.lang.annotation.*;

/**
 * @author xiaoxu123
 * 激活XXl job的配置
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited
public @interface EnablePigXxlJob {

}
