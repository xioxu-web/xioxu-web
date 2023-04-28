package io.microservices.shop.service.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiaoxu123
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("io.microservices.shop.service.user.mapper")
public class ServiceUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceUserApplication.class, args);
    }
}
