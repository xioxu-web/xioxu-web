package io.microservices.shop.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author xiaoxu123
 */
@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"io.microservices.shop"},exclude = {DataSourceAutoConfiguration.class})
public class ShopGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run( ShopGatewayApplication.class, args );
    }

    /**
     * IP 限流
     *
     * @return
     */
    @Bean(name = "ipKeyResolver")
    public KeyResolver userKeyResolver() {
        return exchange -> {
            // 获取远程客户端 IP
            String ip = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostString();
            System.out.println("用户请求的 IP：" + ip);
            return Mono.just(ip);
        };
    }

}