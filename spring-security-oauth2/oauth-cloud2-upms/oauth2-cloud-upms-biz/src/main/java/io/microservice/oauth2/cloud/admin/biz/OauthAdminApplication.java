package io.microservice.oauth2.cloud.admin.biz;

import io.microservice.oauth2.cloud.common.feign.annotation.EnableOauthFeignClients;
import io.microservice.oauth2.cloud.common.security.annotation.EnableOauthResourceServer;
import io.microservice.oauth2.cloud.common.swagger.annotation.EnableOauthDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户统一管理系统
 * @author xiaoxu123
 */
@EnableOauthDoc
@EnableOauthResourceServer
@EnableOauthFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class OauthAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthAdminApplication.class,args);
    }
}
