package com.example.doublebuffer.annotation;
import java.lang.annotation.*;

/**
 * @author xiaoxu123
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DoubleCache {

    String cacheName();

    String key(); //支持SpringEL表达式

    long l2TimeOut() default 120; //设置二级缓存Redis过期时间

    CacheType type() default CacheType.FULL; //操作缓存的类型


}
