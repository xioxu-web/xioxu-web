package cn.iocoder.oauth.framework.tracer.config;

import cn.iocoder.oauth.framework.common.enums.WebFilterOrderEnum;
import cn.iocoder.oauth.framework.tracer.core.aop.BizTraceAspect;
import cn.iocoder.oauth.framework.tracer.core.filter.TraceFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Tracer 配置类
 *
 * @author mashu
 */
@Configuration
@ConditionalOnClass({BizTraceAspect.class})
@EnableConfigurationProperties(TracerProperties.class)
@ConditionalOnProperty(prefix = "oauth.tracer", value = "enable", matchIfMissing = true)
public class OauthTracerAutoConfiguration {

    /**
     * 创建 TraceFilter 过滤器，响应 header 设置 traceId
     */
    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilter() {
        FilterRegistrationBean<TraceFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TraceFilter());
        registrationBean.setOrder( WebFilterOrderEnum.TRACE_FILTER);
        return registrationBean;
    }

}
