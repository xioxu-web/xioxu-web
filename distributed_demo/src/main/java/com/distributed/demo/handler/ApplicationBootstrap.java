package com.distributed.demo.handler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * @author xiaoxu123
 */
@SpringBootApplication(scanBasePackages = { "com.distributed.demo.handler"})
public class ApplicationBootstrap extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicationBootstrap.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationBootstrap.class, args);
    }



}
