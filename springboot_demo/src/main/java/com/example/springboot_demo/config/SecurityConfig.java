package com.example.springboot_demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * @author xiaoxu123
 */
@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("admin").password("admin").roles("ADMIN")
                //配置普通用户
                .and().withUser("normal").password("normal").roles("NORMAL");
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
              .antMatchers("/test/echo").permitAll() //所有用户可以访问
              .antMatchers("/test/admin").hasRole("ADMIN") //需要ADMIN角色可以访问
              .antMatchers("/test/normal").access("hasRole('ROLE_NORMAL')") // 需要 NORMAL 角色
              .anyRequest().permitAll();
        // <Y> 设置 Form 表单登录
            .formLogin()
//                    .loginPage("/login") // 登录 URL 地址
                .permitAll() // 所有用户可访问
                .and()
                // 配置退出相关
                .logout()
//                    .logoutUrl("/logout") // 退出 URL 地址
                .permitAll(); // 所有用户可访问
    }
}
