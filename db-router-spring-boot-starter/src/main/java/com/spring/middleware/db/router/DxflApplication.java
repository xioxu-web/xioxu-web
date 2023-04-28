package com.spring.middleware.db.router;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author xiaoxu123
 */
@SpringBootApplication
@MapperScan(basePackages="com.spring.middleware.db.router.mapper")
@EnableTransactionManagement(order = 10)
public class DxflApplication extends SpringBootServletInitializer implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run( DxflApplication.class, args );
    }

    // Java EE应用服务器配置，
    // 如果要使用tomcat来加载jsp的话就必须继承SpringBootServletInitializer类并且重写其中configure方法
    @Override
    protected
    SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DxflApplication.class);
    }

    // springboot运行后此方法首先被调用
    // 实现CommandLineRunner抽象类中的run方法
    @Override
    public void run(String... args) throws Exception {
        System.out.println("springboot启动完成！");
    }

}