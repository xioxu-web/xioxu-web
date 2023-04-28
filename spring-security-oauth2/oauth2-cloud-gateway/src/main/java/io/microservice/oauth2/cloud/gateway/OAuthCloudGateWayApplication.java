package io.microservice.oauth2.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiaoxu123
 */
@SpringBootApplication(exclude ={DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class OAuthCloudGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthCloudGateWayApplication.class);
    }
}
