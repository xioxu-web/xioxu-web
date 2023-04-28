package cn.iocoder.oauth.framework.operatelog.config;

import cn.iocoder.oauth.framework.operatelog.core.aop.OperateLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoxu123
 */
@Configuration
public class OauthOperateLogAutoConfiguration {
    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }
}
