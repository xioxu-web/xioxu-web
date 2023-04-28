package io.microservice.oauth2.cloud.common.security.component;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

/**
 * @author xiaoxu123
 */
@Configuration
@EnableConfigurationProperties(PermitAllUrlProperties.class)
public class OauthResourceServerAutoConfiguration {

    /**
     *鉴权具体的实现逻辑
     */
    @Bean("permissionService")
    public PermissionService permissionService(){
        return new PermissionService();
    }

    /**
     * 令牌的抽取逻辑
     * @param urlProperties
     * @return BearerTokenExtractor
     */
    @Bean("oauthBearerTokenExtractor")
    public OauthBearerTokenExtractor oauthBearerTokenExtractor(PermitAllUrlProperties urlProperties){
        return new OauthBearerTokenExtractor(urlProperties);
    }

    /**
     * 资源服务器异常处理
     *
     */
    @Bean
    public ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint(ObjectMapper objectMapper, MessageSource securityMessageSource){
        return new ResourceAuthExceptionEntryPoint(objectMapper,securityMessageSource);
    }

    /**
     * 资源服务器toke内省处理器
     * @param authorizationService token 存储实现
     * @return TokenIntrospector
     */
    @Bean
    public OpaqueTokenIntrospector opaqueTokenIntrospector(OAuth2AuthorizationService authorizationService) {
        return new OauthCustomOpaqueTokenIntrospector(authorizationService);
    }






}
