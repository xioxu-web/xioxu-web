package io.microservice.oauth2.cloud.auth.server.config;

import io.microservice.oauth2.cloud.auth.server.support.CustomeOAuth2AccessTokenGenerator;
import io.microservice.oauth2.cloud.auth.server.support.core.CustomeOAuth2TokenCustomizer;
import io.microservice.oauth2.cloud.auth.server.support.core.FormIdentityLoginConfigurer;
import io.microservice.oauth2.cloud.auth.server.support.core.Oauth2DaoAuthenticationProvider;
import io.microservice.oauth2.cloud.auth.server.support.handler.Oauth2AuthenticationFailureEventHandler;
import io.microservice.oauth2.cloud.auth.server.support.handler.Oauth2AuthenticationSuccessEventHandler;
import io.microservice.oauth2.cloud.auth.server.support.password.OAuth2ResourceOwnerPasswordAuthenticationConverter;
import io.microservice.oauth2.cloud.auth.server.support.password.OAuth2ResourceOwnerPasswordAuthenticationProvider;
import io.microservice.oauth2.common.core.constant.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.*;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author xiaoxu123
 * 认证服务器配置
 */
@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfiguration{

    @Resource
    private final OAuth2AuthorizationService authorizationService;

    
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer<>();

        http.apply(authorizationServerConfigurer.tokenEndpoint((tokenEndpoint) -> {// 个性化认证授权端点
            tokenEndpoint
                    .accessTokenResponseHandler(new Oauth2AuthenticationSuccessEventHandler()) // 登录成功处理器
                    .errorResponseHandler(new Oauth2AuthenticationFailureEventHandler());// 登录失败处理器
        }).authorizationEndpoint( // 授权码端点个性化confirm页面
                authorizationEndpoint -> authorizationEndpoint.consentPage(SecurityConstants.CUSTOM_CONSENT_PAGE_URI)));

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
        DefaultSecurityFilterChain securityFilterChain = http.requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .apply(authorizationServerConfigurer.authorizationService(authorizationService)// redis存储token的实现
                        .providerSettings(ProviderSettings.builder().build()))
                // 授权码登录的登录页个性化
                .and().apply(new FormIdentityLoginConfigurer()).and().build();

        // 注入自定义授权模式实现
        addCustomOAuth2GrantAuthenticationProvider(http);
        return securityFilterChain;
    }

    /**
     * 令牌生成规则实现 </br>
     * client:username:uuid
     * @return OAuth2TokenGenerator
     */
    @Bean
    public OAuth2TokenGenerator oAuth2TokenGenerator() {
        CustomeOAuth2AccessTokenGenerator accessTokenGenerator = new CustomeOAuth2AccessTokenGenerator();
        // 注入Token 增加关联用户信息
        accessTokenGenerator.setAccessTokenCustomizer( new CustomeOAuth2TokenCustomizer() );
        return new DelegatingOAuth2TokenGenerator( accessTokenGenerator, new OAuth2RefreshTokenGenerator() );
    }
    /**
     * request -> xToken 注入请求转换器
     * @return DelegatingAuthenticationConverter
     */
    private
    AuthenticationConverter accessTokenRequestConverter() {
        return new DelegatingAuthenticationConverter( Arrays.asList(
                new OAuth2ResourceOwnerPasswordAuthenticationConverter(),
                new OAuth2RefreshTokenAuthenticationConverter(),
                new OAuth2ClientCredentialsAuthenticationConverter(),
                new OAuth2AuthorizationCodeAuthenticationConverter(),
                new OAuth2AuthorizationCodeRequestAuthenticationConverter()));
    }

    /**
     * 注入授权模式实现提供方
     *
     * 1. 密码模式 </br>
     *
     */
    @SuppressWarnings("unchecked")
    private void addCustomOAuth2GrantAuthenticationProvider(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);

        OAuth2ResourceOwnerPasswordAuthenticationProvider resourceOwnerPasswordAuthenticationProvider = new OAuth2ResourceOwnerPasswordAuthenticationProvider(
                authenticationManager, authorizationService, oAuth2TokenGenerator());

        // 处理 UsernamePasswordAuthenticationToken
        http.authenticationProvider(new Oauth2DaoAuthenticationProvider());
        // 处理 OAuth2ResourceOwnerPasswordAuthenticationToken
        http.authenticationProvider(resourceOwnerPasswordAuthenticationProvider);

    }
}
