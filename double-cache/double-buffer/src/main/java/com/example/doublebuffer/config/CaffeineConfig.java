package com.example.doublebuffer.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaoxu123
 * @deprecated caffeineCache配置
 */
@Configuration
public class CaffeineConfig {

    @Bean
    public Cache<String,Object> caffeineCache(){
      return Caffeine.newBuilder()
              .initialCapacity(128) //初始化大小
              .maximumSize(124) //最大容量
              .expireAfterWrite(60, TimeUnit.SECONDS)
              .build();
    }
}
