package cn.iocoder.oauth.framework.apilog.config;

import cn.iocoder.oauth.framework.apilog.core.filter.ApiAccessLogFilter;
import cn.iocoder.oauth.framework.apilog.core.service.ApiAccessLogFrameworkService;
import cn.iocoder.oauth.framework.common.enums.WebFilterOrderEnum;
import cn.iocoder.oauth.framework.web.config.WebProperties;
import cn.iocoder.oauth.framework.web.config.OauthWebAutoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.Filter;

/**
 * @author xiaoxu123
 */
@Configuration
@AutoConfigureAfter(OauthWebAutoConfiguration.class)
public class OauthApiLogAutoConfiguration {

    /**
     * 创建 ApiAccessLogFilter Bean，记录 API 请求日志
     */
   @Bean
    public FilterRegistrationBean<ApiAccessLogFilter> apiAccessLogFilter(WebProperties webProperties,
                                                                         @Value("${spring.application.name}") String applicationName,
                                                                         ApiAccessLogFrameworkService apiAccessLogFrameworkService) {
        ApiAccessLogFilter filter = new ApiAccessLogFilter(webProperties, applicationName, apiAccessLogFrameworkService);
        return createFilterBean(filter );
    }

    private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder( WebFilterOrderEnum.API_ACCESS_LOG_FILTER );
        return bean;
    }

}
