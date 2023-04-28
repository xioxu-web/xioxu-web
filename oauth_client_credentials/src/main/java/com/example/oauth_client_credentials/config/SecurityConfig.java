package com.example.oauth_client_credentials.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
/**
 *
 * @author xiaoxu123
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 创建UserDetailsService Bean
     */
    @Override
    @Bean(name =BeanIds.USER_DETAILS_SERVICE)
    public UserDetailsService userDetailsServiceBean() throws Exception {
      return super.userDetailsServiceBean();
    }

    @Bean
    public static NoOpPasswordEncoder passwordEncoder(){
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                 auth.
                // 使用内存中的 InMemoryUserDetailsManager
                        inMemoryAuthentication()
                // 不使用 PasswordEncoder 密码编码器
                .passwordEncoder(passwordEncoder())
                // 配置 yunai 用户
                .withUser("xiaoxu").password("123456").roles("USER");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                //设置token/remoke/demo无需授权
                .antMatchers("/token/demo/revokeToken").permitAll()
                //设置其他的接口需要授权
                .anyRequest().authenticated();
    }
}
