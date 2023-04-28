package com.example.middleware_design.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * 拦截所有request请求
 * @author xiaoxu123
 */
@Configuration
public class WebMvcConfigs implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }
}
