package io.microservice.oauth2.cloud.redis.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xiaoxu123
 */
@Data
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

    @Value("${spring.redis.host:127.0.0.1}")
    private String host;

    @Value("${spring.redis.port:6379}")
    private int port;

    @Value("${spring.redis.timeout:15000}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-idle:300}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait:15000}")
    private long maxWaitMillis;

    @Value("${spring.redis.database:0}")
    private int database;

}
