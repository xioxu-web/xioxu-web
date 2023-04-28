package com.example.middleware_design.aop;

import com.example.middleware_design.annotation.DoRateLimiter;
import com.example.middleware_design.value.IValveService;
import com.example.middleware_design.value.Impl.RateLimiterValve;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class DoRateLimiterPoint {
    private ConcurrentHashMap<String, RateLimiter> rateLimiters=new ConcurrentHashMap<>();

    // 拦截所有被注解DoRateLimiter标注的方法
    @Pointcut("@annotation(com.example.middleware_design.annotation.DoRateLimiter)")
    public void aopPoint(){
    }

    /**
     *环绕通知
     *
     */
    @Around("aopPoint()")
    public Object doBefore(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (null == method) {
            return null;
        }
        DoRateLimiter doRateLimiter = method.getDeclaredAnnotation(DoRateLimiter.class);
        if (doRateLimiter == null) {
            return proceedingJoinPoint.proceed();
        }
        double permitsPerSecond = doRateLimiter.permitsPerSecond();
        long timeout = doRateLimiter.timeout();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String requestURI = requestAttributes.getRequest().getRequestURI();
        RateLimiter rateLimiter = rateLimiters.get(requestURI);
        if (rateLimiter == null) {
            rateLimiter= RateLimiter.create(permitsPerSecond);
            RateLimiter rateLimiterPrevious = rateLimiters.putIfAbsent(requestURI, rateLimiter);
            if(rateLimiterPrevious!=null){
               rateLimiter=rateLimiterPrevious;
            }
        }
        //触发限流
        boolean tryAcquire = rateLimiter.tryAcquire(timeout, TimeUnit.MILLISECONDS);
        if(!tryAcquire){
            System.out.println(LocalTime.now()+""+requestURI+"触发限流");
            doFallback();
            return  null;
        }
        System.out.println(LocalTime.now()+""+requestURI+"请求成功");
        return  proceedingJoinPoint.proceed();
    }

    private void doFallback(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer=null;
        try {
            writer = response.getWriter();
            writer.println("系统忙，请稍后再试");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }



}
