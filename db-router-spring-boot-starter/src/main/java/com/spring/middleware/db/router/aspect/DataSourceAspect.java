package com.spring.middleware.db.router.aspect;
import com.spring.middleware.db.router.dynamic.DynamicDataSourceHolder;
import com.spring.middleware.db.router.annotation.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 定义数据源的AOP切面，通过该Service的方法名判断是应该走读库还是写库
 * @author xiaoxu123
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DataSourceAspect {

    private static final Logger log= LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("!@annotation(com.spring.middleware.db.router.annotation.DataSource) " +
            "&& (execution(* com.spring.middleware.db.router.service.*.select*(..)) " +
            "|| execution(* com.spring.middleware.db.router.service..*.find*(..)))")
    public void readPointcut(){

    }

    @Pointcut("@annotation(com.spring.middleware.db.router.annotation.DataSource) " +
            "|| execution(* com.spring.middleware.db.router.service..*.save*(..)) " +
            "|| execution(* com.spring.middleware.db.router.service..*.add*(..)) " +
            "|| execution(* com.spring.middleware.db.router.service..*.insert*(..)) " +
            "|| execution(* com.spring.middleware.db.router.service..*.update*(..)) " +
            "|| execution(* com.spring.middleware.db.router.service..*.edit*(..)) " +
            "|| execution(* com.spring.middleware.db.router.service..*.delete*(..)) " +
            "|| execution(* com.spring.middleware.db.router.service..*.remove*(..))")
    public void writePointcut(){

    }

    /**
     * 配置前置通知，使用在方法readPointcut()上注册的切入点
     */
    @Before("readPointcut()")
    public void before(JoinPoint point){
      //获取当前方法信息
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        //判断方法上是否存在注解@Datasource
        boolean present = method.isAnnotationPresent(DataSource.class );
        if(!present){
         //如果不存在，默认走从库读
            DynamicDataSourceHolder.slave();
        }else{
            //如果存在，走主库读
            DynamicDataSourceHolder.master();
        }
    }

    @Before("writePointcut()")
    public void write() {
        DynamicDataSourceHolder.master();
    }

}
