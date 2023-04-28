package com.example.middleware_design.aop;

import com.example.middleware_design.annotation.GPHystrixCommand;
import com.example.middleware_design.utils.BeforeAdviceMethodInvocationAdapter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

import javax.annotation.PreDestroy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.*;
/**
 * 熔断Aop切面类
 * @author xiaoxu123
 */
public class HystrixAnnotationAuthorizingAspect {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;

    private Semaphore semaphore = null;

    ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(QUEUE_CAPACITY),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @Pointcut("@annotation(com.example.middleware_design.annotation.GPHystrixCommand)")
    void anyRPCAnnotatedMethodCall() {
    }

    @Around("anyRPCAnnotatedMethodCall()")
    public Object executeAnnotatedMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        BeforeAdviceMethodInvocationAdapter before = BeforeAdviceMethodInvocationAdapter.createFrom(joinPoint);
        Method method = before.getMethod();
        Object[] arguments = before.getArguments();
        Object res = null;
        if (method.isAnnotationPresent(GPHystrixCommand.class)) {
            GPHystrixCommand annotation = method.getAnnotation(GPHystrixCommand.class);
            int timeout = annotation.timeout();
            int semaphoreValue = annotation.semaphore();
            String fallback = annotation.fallback();
            if (0 == timeout && 0 == semaphoreValue || 0 != timeout) {
                Future<Object> future = poolExecutor.submit(() -> {
                    Object returnValue = null;
                    try {
                        returnValue = joinPoint.proceed();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    return returnValue;
                });
                //100毫秒超时时间
                try {
                    res = future.get(timeout,TimeUnit.MILLISECONDS);
                } catch (InterruptedException | TimeoutException e) {
                    future.cancel(true);
                    res = invokeFallbackMethod(method, joinPoint.getTarget(), fallback,arguments);
                }
            }
            if(0!=semaphoreValue){
               if(semaphore==null){
                   semaphore = new Semaphore(semaphoreValue);
               }
                try {
                    semaphore.acquire();
                    res = joinPoint.proceed(arguments);
                }finally {
                    semaphore.release();
                }
                }
            return res;

            }
         return null;
    }

      //寻找回调方法
     private Object invokeFallbackMethod(Method method, Object bean, String fallback, Object[] arguments) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
         // 查找 fallback 方法
         Method fallbackMethod = findFallbackMethod(method, bean, fallback);
         return fallbackMethod.invoke(bean, arguments);
     }

    private Method findFallbackMethod(Method method, Object bean, String fallbackMethodName) throws
            NoSuchMethodException {
        // 通过被拦截方法的参数类型列表结合方法名，从同一类中找到 fallback 方法
        Class beanClass = bean.getClass();
        Method fallbackMethod = beanClass.getMethod(fallbackMethodName, method.getParameterTypes());
        return fallbackMethod;
    }


    @PreDestroy
    private void destroy(){
     poolExecutor.shutdown();
    }

}
