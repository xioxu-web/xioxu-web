package com.example.oauth2ssoauthserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author xiaoxu123
 */
@Configuration
public class AccessTokenConfig {

    /**
     * 令牌的存储策略
     */
    @Bean
    public TokenStore tokenStore() {
        //使用JwtTokenStore生成JWT令牌
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * JwtAccessTokenConverter
     * TokenEnhancer的子类，在JWT编码的令牌值和OAuth身份验证信息之间进行转换。
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenEnhancer();
        // 设置秘钥
        converter.setSigningKey(TokenConstant.SIGN_KEY);
        /*
         * 设置自定义得的令牌转换器，从map中转换身份信息
         * fix(*)：修复刷新令牌无法获取用户详细信息的问题
         */
        converter.setAccessTokenConverter(new JwtEnhanceAccessTokenConverter());
        return converter;
    }

    /**
     * JWT令牌增强，继承JwtAccessTokenConverter
     * 将业务所需的额外信息放入令牌中，这样下游微服务就能解析令牌获取
     */
    public static class JwtAccessTokenEnhancer extends JwtAccessTokenConverter {
        /**
         * 重写enhance方法，在其中扩展
         */
        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            Object principal = authentication.getUserAuthentication().getPrincipal();
            if (principal instanceof SecurityUser){
                //获取userDetailService中查询到用户信息
                SecurityUser user=(SecurityUser)principal;
                //将额外的信息放入到LinkedHashMap中
                LinkedHashMap<String,Object> extendInformation=new LinkedHashMap<>();
                //设置用户的userId
                extendInformation.put(TokenConstant.USER_ID,user.getUserId());
                //添加到additionalInformation
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(extendInformation);
            }
            return super.enhance(accessToken, authentication);
        }
    }
}
