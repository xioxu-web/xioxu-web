package com.example.springboot_demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/**
 * @author xiaoxu123
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = {"com.example.springboot_demo.*"})
public class SpringbootDemoApplication extends SpringBootServletInitializer implements CommandLineRunner {

    // 入口
    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
    }

    // Java EE应用服务器配置，
    // 如果要使用tomcat来加载jsp的话就必须继承SpringBootServletInitializer类并且重写其中configure方法
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringbootDemoApplication.class);
    }

    // springboot运行后此方法首先被调用
    // 实现CommandLineRunner抽象类中的run方法
    @Override
    public void run(String... args) throws Exception {
        System.out.println("springboot启动完成！");
    }

}
