package com.example.doublebuffer.aspect;

import com.example.business.constant.CacheConstant;
import com.example.doublebuffer.annotation.CacheType;
import com.example.doublebuffer.annotation.DoubleCache;
import com.example.doublebuffer.util.ElParser;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoxu123
 * @deprecated  操作缓存切面类
 */

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class CacheAspect {

    private final Cache cache;

    private final RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.example.doublebuffer.annotation.DoubleCache)")
    public void cacheAspect(){

    }

    @Around("cacheAspect()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        //拼接解析SpringEL表达式的map
        String[] parameterNames = signature.getParameterNames();
        Object[] args = point.getArgs();

        TreeMap<String, Object> treeMap = new TreeMap<>();

        for (int i = 0; i < parameterNames.length; i++) {
           treeMap.put(parameterNames[i],args[i]);
        }

        DoubleCache annotation = method.getAnnotation( DoubleCache.class );
        String elResult = ElParser.parse( annotation.key(), treeMap );

        String realKey = annotation.cacheName() + CacheConstant.COLON + elResult;

        //强制更新
        if(annotation.type()== CacheType.PUT){
            Object object = point.proceed();
            redisTemplate.opsForValue().set(realKey, object,annotation.l2TimeOut(), TimeUnit.SECONDS);
            cache.put(realKey,object);
            return object;

        //删除
        }else if (annotation.type()==CacheType.DELETE){
           redisTemplate.delete(realKey);
           cache.invalidate(realKey);

           return point.proceed();
        }

        //读写, 查询Caffeine
        Object caffeineCache  = cache.getIfPresent( realKey );
        if(Objects.nonNull(caffeineCache)){
            log.info("get data from db");
            return caffeineCache;
        }

        //查询Redis
        Object redisCache  = redisTemplate.opsForValue().get( realKey );
        if(Objects.nonNull(redisCache)){
            log.info("get data from redis");
            cache.put(realKey,redisCache);

            return redisCache;
        }

        log.info("get data from database");
        Object object = point.proceed();
        if(Objects.nonNull(object)){

            //写回redisTemplate
            redisTemplate.opsForValue().set(realKey,object,annotation.l2TimeOut(),TimeUnit.SECONDS);

            //写回Caffeine
            cache.put(realKey,object);

        }

        return  object;


    }

}
