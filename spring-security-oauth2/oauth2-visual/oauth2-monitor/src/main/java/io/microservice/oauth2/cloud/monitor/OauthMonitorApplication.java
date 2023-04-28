package io.microservice.oauth2.cloud.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiaoxu123
 */
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class OauthMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run( OauthMonitorApplication.class, args );
    }


}
