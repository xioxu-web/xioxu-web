package io.microservice.oauth2.cloud.monitor.config;

import lombok.SneakyThrows;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * @author xiaoxu123
 */
@Configuration(proxyBeanMethods = false)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final String adminContextPath;


     public WebSecurityConfigurer(AdminServerProperties adminServerProperties){
        this.adminContextPath=adminServerProperties.getContextPath();
     }


    @Override
    @SneakyThrows
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminContextPath+"/");

        http
              .headers().frameOptions().disable()
              .and().authorizeRequests()
              .antMatchers( adminContextPath+"/assert"
                      ,adminContextPath+"/login"
                      ,adminContextPath+"/instances/**"
                      ,adminContextPath+"//actuator/**"
              ).permitAll()
              .anyRequest().authenticated()
              .and()
              .formLogin().loginPage(adminContextPath+"/login")
              .successHandler(successHandler)
              .and()
              .logout().logoutUrl(adminContextPath+"/logout")
              .and()
              .httpBasic().and()
              .csrf()
              .disable();
    }
}
