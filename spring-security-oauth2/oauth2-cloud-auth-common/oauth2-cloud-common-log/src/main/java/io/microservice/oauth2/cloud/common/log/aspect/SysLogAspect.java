package io.microservice.oauth2.cloud.common.log.aspect;

import cn.hutool.core.util.StrUtil;
import io.microservice.oauth2.cloud.common.log.event.SysLogEvent;
import io.microservice.oauth2.cloud.common.log.util.LogTypeEnum;
import io.microservice.oauth2.cloud.common.log.util.SysLogUtils;
import io.microservice.oauth2.common.core.util.SpringContextHolder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;

/**
 * @author xiaoxu123
 */
@Aspect
@Slf4j
public class SysLogAspect {


  @SneakyThrows
  @Around("@annotation(sysLog)")
  public Object around(ProceedingJoinPoint point, io.microservice.oauth2.cloud.common.log.annotation.SysLog sysLog)  {
      String strClassName  = point.getTarget().getClass().getName();
      String strMethodName  = point.getSignature().getName();
      log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);
      String value = sysLog.value();
      String expression = sysLog.expression();
      // 当前表达式存在 SPEL，会覆盖 value 的值
      if (StrUtil.isNotBlank(expression)) {
          // 解析SPEL
          MethodSignature signature = (MethodSignature) point.getSignature();
          EvaluationContext context = SysLogUtils.getContext(point.getArgs(), signature.getMethod());
          try {
           value = SysLogUtils.getValue(context, expression, String.class);
          }
          catch (Exception e) {
              // SPEL 表达式异常，获取 value 的值
              log.error("@SysLog 解析SPEL {} 异常", expression);
          }
      }
      io.microservice.oauth2.cloud.admin.api.entity.SysLog logVo = SysLogUtils.getSysLog();
      logVo.setTitle(value);
      // 发送异步日志事件
      long startTime= System.currentTimeMillis();
      Object obj;

      try {
          obj=point.proceed();
      } catch (Exception e) {
         logVo.setType( LogTypeEnum.ERROR.getType() );
         logVo.setException(e.getMessage());
         throw e;
      }finally {
          Long endTime = System.currentTimeMillis();
          logVo.setTime(endTime - startTime);
          SpringContextHolder.publishEvent(new SysLogEvent(logVo));
      }
      return obj;
  }
}
