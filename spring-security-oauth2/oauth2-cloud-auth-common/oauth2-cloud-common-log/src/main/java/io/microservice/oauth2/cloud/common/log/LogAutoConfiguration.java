package io.microservice.oauth2.cloud.common.log;

import io.microservice.oauth2.cloud.admin.api.fegin.RemoteLogService;
import io.microservice.oauth2.cloud.common.log.aspect.SysLogAspect;
import io.microservice.oauth2.cloud.common.log.event.SysLogListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * 日志自动配置
 * @author xiaoxu123
 */
@EnableAsync
@RequiredArgsConstructor
@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
public class LogAutoConfiguration {

    @Bean
    public SysLogListener sysLogListener(RemoteLogService remoteLogService){
      return new SysLogListener(remoteLogService);
    }

    @Bean
    public SysLogAspect sysLogAspect(){
      return new SysLogAspect();
    }

}
