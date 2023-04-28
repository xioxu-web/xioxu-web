package io.microservices.shop.user.auth;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author xiaoxu123
 */
@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"io.microservices.shop"})
public class ShopAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopAuthApplication.class, args);
    }

}
