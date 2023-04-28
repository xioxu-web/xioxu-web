package com.example.mybatis_plus_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xiaoxu123
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.example.mybatis_plus_demo.Mapper"})
public class MybatisPlusDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusDemoApplication.class, args);
    }


}
