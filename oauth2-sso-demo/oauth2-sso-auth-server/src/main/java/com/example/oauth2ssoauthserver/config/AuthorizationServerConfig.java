package com.example.oauth2ssoauthserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;


/**
 * @author xiaoxu123
 * @deprecated 授权服务配置类
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private  DataSource dataSource;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private  TokenStore tokenStore;


    /**
     * 客户端存储策略，这里使用内存方式，后续可以存储在数据库
     */
    @Autowired
    private ClientDetailsService clientDetailsService;

    /**
     * 授权认证管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security.tokenKeyAccess("isAuthenticated()");
    }


    /**
     * 客户端信息配置
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //使用JdbcClientDetailsService，从数据库中加载客户端的信息
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
       /* clients.inMemory()
                .withClient( "changgou" )        //客户端id
                .secret( "changgou" )                   //秘钥
                .redirectUris("http://localhost" )     //重定向地址
                .accessTokenValiditySeconds( 120 )      //访问令牌有效期
                .refreshTokenValiditySeconds( 120 )     //刷新令牌有效期
                .authorizedGrantTypes(
                        "authorization_code",          //根据授权码生成令牌
                        "client_credentials",          //客户端认证
                        "refresh_token",               //刷新令牌
                        "password" )                    //密码方式认证
                .scopes( "app" );                        //客户端范围，名称自定义，必填*/

    }

    /**
     * 令牌管理服务的配置
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        //客户端端配置策略
        services.setClientDetailsService(clientDetailsService);
        //支持令牌的刷新
        services.setSupportRefreshToken(true);
        //令牌服务
        services.setTokenStore(tokenStore);
        //access_token的过期时间
        services.setAccessTokenValiditySeconds(60 * 60 * 24 * 3);
        //refresh_token的过期时间
        services.setRefreshTokenValiditySeconds(60 * 60 * 24 * 3);

        //设置令牌增强，使用JwtAccessTokenConverter进行转换
        services.setTokenEnhancer(jwtAccessTokenConverter);
        return services;
    }




    /**
     * 授权服务器端点配置
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.accessTokenConverter(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager)  // 认证管理器
                .tokenStore(tokenStore)                        // 令牌存储
                .userDetailsService(userDetailsService);       // 用户信息service
    }


    /**
     * 授权码模式的service，使用授权码模式authorization_code必须注入
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }






}
