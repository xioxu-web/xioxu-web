package com.orc.rpc.annotation;

import java.lang.annotation.*;

/**
 * 该注解用于注入远程服务
 * @author xiaoxu123
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InjectService {
      
}
