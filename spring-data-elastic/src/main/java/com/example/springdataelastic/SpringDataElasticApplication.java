package com.example.springdataelastic;

import com.example.springdataelastic.config.app.AppConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/**
 * @author xiaoxu123
 */
@SpringBootApplication
@MapperScan({"com.example.springdataelastic.mapper"})
public class SpringDataElasticApplication {

    public static void main(String[] args) {
        SpringApplication.run( SpringDataElasticApplication.class, args );
    }



}
