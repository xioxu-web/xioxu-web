package io.microservices.shop.member;

import io.microservices.shop.common.config.WebMvcConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * @author xiaoxu123
 */
@EnableDiscoveryClient
@MapperScan("io.microservices.shop.member.mapper")
@SpringBootApplication(scanBasePackages = "io.microservices.shop")
@Import({WebMvcConfig.class})
public class ShopMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopMemberApplication.class, args);
    }

}
