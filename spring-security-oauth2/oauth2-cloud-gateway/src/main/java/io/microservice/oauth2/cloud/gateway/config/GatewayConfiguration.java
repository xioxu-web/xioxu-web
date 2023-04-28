package io.microservice.oauth2.cloud.gateway.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.microservice.oauth2.cloud.gateway.filter.OauthRequestGlobalFilter;
import io.microservice.oauth2.cloud.gateway.filter.PasswordDecoderFilter;
import io.microservice.oauth2.cloud.gateway.filter.SwaggerBasicGatewayFilter;
import io.microservice.oauth2.cloud.gateway.filter.ValidateCodeGatewayFilter;
import io.microservice.oauth2.cloud.gateway.handler.GlobalExceptionHandler;
import io.microservice.oauth2.cloud.gateway.handler.ImageCodeHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
/**
 * @author xiaoxu123
 * 网关配置
 */
@Configuration
@EnableConfigurationProperties(GatewayConfigProperties.class)
public class GatewayConfiguration {

    @Bean
    public PasswordDecoderFilter passwordDecoderFilter(GatewayConfigProperties gatewayConfigProperties){
      return new PasswordDecoderFilter( gatewayConfigProperties);
    }

    @Bean
    public OauthRequestGlobalFilter oauthRequestGlobalFilter(){
        return new OauthRequestGlobalFilter();
    }

    @Bean
    @ConditionalOnProperty(name = "swagger.basic.enabled")
    public SwaggerBasicGatewayFilter swaggerBasicGatewayFilter(
            SpringDocConfiguration.SwaggerDocProperties swaggerProperties) {
        return new SwaggerBasicGatewayFilter(swaggerProperties);
    }

    @Bean
    public ValidateCodeGatewayFilter validateCodeGatewayFilter(GatewayConfigProperties configProperties,
                                                        ObjectMapper objectMapper, RedisTemplate redisTemplate) {
        return new ValidateCodeGatewayFilter(configProperties, objectMapper, redisTemplate);
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler(ObjectMapper objectMapper) {
        return new GlobalExceptionHandler(objectMapper);
    }

    @Bean
    public ImageCodeHandler imageCodeHandler(RedisTemplate redisTemplate) {
        return new ImageCodeHandler(redisTemplate);
    }


}
