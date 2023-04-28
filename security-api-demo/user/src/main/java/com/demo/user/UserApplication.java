package com.demo.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiaoxu123
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UserApplication{

    public static void main(String[] args) {
       SpringApplication.run(UserApplication.class);
    }
}
