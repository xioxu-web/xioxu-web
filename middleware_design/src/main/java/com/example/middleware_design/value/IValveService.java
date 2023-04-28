package com.example.middleware_design.value;

import com.example.middleware_design.annotation.DoRateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/*
*  RateLimiter的封装
*
* */
public interface IValveService {

  /*  Object access(ProceedingJoinPoint jp, Method method, DoRateLimiter doRateLimiter, Object[] args) throws Throwable;*/
}
