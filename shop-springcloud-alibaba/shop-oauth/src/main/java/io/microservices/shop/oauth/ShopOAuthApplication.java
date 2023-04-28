package io.microservices.shop.oauth;
import io.microservices.shop.oauth.util.RtErrorHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * @author xiaoxu123
 */
@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"io.microservices.shop.service.user.api.fegin"})
@SpringBootApplication(scanBasePackages = {"io.microservices.shop"})
public class ShopOAuthApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ShopOAuthApplication.class);
        springApplication.setAllowBeanDefinitionOverriding(true);
        springApplication.run(args);
    }

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(new RtErrorHandler());
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<>(16);
        mediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        mediaTypes.add(MediaType.APPLICATION_CBOR);
        mediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        converter.setSupportedMediaTypes(mediaTypes);
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }
}
