package io.microservice.oauth2.cloud.common.datasource.annotation;


import java.lang.annotation.*;

/**
 * 开启动态数据源
 * @author xiaoxu123
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableDynamicDataSource {
}
