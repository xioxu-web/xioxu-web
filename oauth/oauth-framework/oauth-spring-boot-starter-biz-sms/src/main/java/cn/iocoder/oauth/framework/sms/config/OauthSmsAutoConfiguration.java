package cn.iocoder.oauth.framework.sms.config;

import cn.iocoder.oauth.framework.sms.core.client.Impl.SmsClientFactoryImpl;
import cn.iocoder.oauth.framework.sms.core.client.SmsClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoxu123
 */
@Configuration
public class OauthSmsAutoConfiguration {

    @Bean
    public SmsClientFactory smsClientFactory() {
        return new SmsClientFactoryImpl();
    }
}
