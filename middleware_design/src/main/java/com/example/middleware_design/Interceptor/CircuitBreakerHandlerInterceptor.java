package com.example.middleware_design.Interceptor;

import com.example.middleware_design.annotation.GPHystrixCommand;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author xiaoxu123
 */
public class CircuitBreakerHandlerInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
           HandlerMethod handlerMethod=(HandlerMethod)handler;
            GPHystrixCommand timeOut= handlerMethod.getMethodAnnotation(GPHystrixCommand.class);
            if(null!=timeOut){
                int timeout = timeOut.timeout();
                String fallback = timeOut.fallback();
                Object bean = handlerMethod.getBean();
                Method method = handlerMethod.getMethod();

            }
        }
        return false;
    }
}
