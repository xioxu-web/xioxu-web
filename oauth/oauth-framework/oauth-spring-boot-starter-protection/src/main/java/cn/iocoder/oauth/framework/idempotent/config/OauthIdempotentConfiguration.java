package cn.iocoder.oauth.framework.idempotent.config;

import cn.iocoder.oauth.framework.idempotent.core.aop.IdempotentAspect;
import cn.iocoder.oauth.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import cn.iocoder.oauth.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import cn.iocoder.oauth.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import cn.iocoder.oauth.framework.idempotent.core.redis.IdempotentRedisDAO;
import cn.iocoder.oauth.framework.redis.config.OauthRedisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(OauthRedisAutoConfiguration.class)
public class OauthIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
