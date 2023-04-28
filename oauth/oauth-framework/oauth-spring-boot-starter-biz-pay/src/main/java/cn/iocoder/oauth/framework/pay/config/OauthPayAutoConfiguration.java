package cn.iocoder.oauth.framework.pay.config;

import cn.iocoder.oauth.framework.pay.core.client.Impl.PayClientFactoryImpl;
import cn.iocoder.oauth.framework.pay.core.client.PayClientFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 支付配置类
 * @author xiaoxu123
 */
@Configuration
@EnableConfigurationProperties(value =PayProperties.class)
public class OauthPayAutoConfiguration {

    @Bean
    public PayClientFactory payClientFactory(){
      return new PayClientFactoryImpl();
    }
}
