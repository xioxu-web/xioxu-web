package io.microservice.oauth2.cloud.common.feign.sentinel;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import feign.Feign;
import io.microservice.oauth2.cloud.common.feign.sentinel.ext.OauthSentinelFeign;
import io.microservice.oauth2.cloud.common.feign.sentinel.handler.OauthUrlBlockHandler;
import io.microservice.oauth2.cloud.common.feign.sentinel.parser.OauthHeaderRequestOriginParser;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author xiaoxu123
 * Sentinel配置
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class SentinelAutoConfiguration {


    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "feign.sentinel.enabled")
    public Feign.Builder feignSentinelBuilder() {
        return OauthSentinelFeign.builder();
    }

    @Bean
    @ConditionalOnMissingBean
    public BlockExceptionHandler blockExceptionHandler() {
        return new OauthUrlBlockHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public
    RequestOriginParser requestOriginParser() {
        return new OauthHeaderRequestOriginParser();
    }


}
