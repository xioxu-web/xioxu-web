package com.example.middleware_design.config;

import com.example.middleware_design.Interceptor.RequestLimiterInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author xiaoxu123
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 请求限流拦截器
     */
    @Autowired
    protected RequestLimiterInterceptor requestLimiterInterceptor;

    public WebMvcConfig(){

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 请求限流
        registry.addInterceptor(requestLimiterInterceptor).addPathPatterns("/**");
    }

}
