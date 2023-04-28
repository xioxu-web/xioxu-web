package io.microservice.oauth2.cloud.auth.server;
import io.microservice.oauth2.cloud.common.feign.annotation.EnableOauthFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author admin
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableOauthFeignClients
@EnableCaching
public class OAuthCloudAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthCloudAuthServerApplication.class);
    }
}
