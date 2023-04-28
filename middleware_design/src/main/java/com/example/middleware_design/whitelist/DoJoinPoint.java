package com.example.middleware_design.whitelist;

import com.alibaba.fastjson.JSON;
import com.example.middleware_design.annotation.DoWhiteList;
import com.example.middleware_design.config.StarterService;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xiaoxu123
 */
@Component
@Aspect
public class DoJoinPoint {

    @Autowired
    private StarterService starterService;

    @Pointcut("@annotation(com.example.middleware_design.annotation.DoWhiteList)")
    public void aopPoint() {

    }

    @Around("aopPoint()")
    public Object doRouter(ProceedingJoinPoint jp) throws Throwable {
        Method method = getMethod(jp);
        DoWhiteList doWhiteList = method.getAnnotation(DoWhiteList.class);
        String filedValue = getFiledValue(doWhiteList.key(), jp.getArgs());
        if(null==filedValue || "".equals(filedValue)) {
            return jp.proceed();
        }
        String[] split =starterService.split(",");
        //白名单控制
        for (String str:split) {
          if(str.equals(filedValue)){
             return jp.proceed();
          }
        }
        return returnObject(doWhiteList,method);
    }

    private Method getMethod(ProceedingJoinPoint jp) throws NoSuchMethodException {
        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(),methodSignature.getParameterTypes());
    }

    private Class<? extends Object> getClass(JoinPoint jp) throws NoSuchMethodException {
        return jp.getTarget().getClass();
    }
    //返回对象
    private Object returnObject(DoWhiteList whiteList,Method method) throws IllegalAccessException, InstantiationException {
        Class<?> returnType = method.getReturnType();
        String returnJson = whiteList.returnJson();
        if("".equals(returnJson)){
          return  returnType.newInstance();
        }
        return JSON.parseObject(returnJson,returnType);
    }

    //获取属性值
    private String getFiledValue(String filed,Object[]args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String filedValue = null;
        for (Object arg : args) {
            try {

                if (null == filedValue || "".equals(filedValue)) {
                    BeanUtils.getProperty(arg, filed);
                } else {
                    break;
                }
            } catch (Exception e) {
                if (args.length == 1) {
                    return args[0].toString();
                }

            }
        }
        return filedValue;

    }

}
