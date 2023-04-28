package io.microservice.oauth2.cloud.common.datasource;

import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import io.microservice.oauth2.cloud.common.datasource.config.DataSourceProperties;
import io.microservice.oauth2.cloud.common.datasource.config.JdbcDynamicDataSourceProvider;
import io.microservice.oauth2.cloud.common.datasource.config.LastParamDsProcessor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoxu123
 * 动态切换数据源
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(DataSourceProperties.class)
public class DynamicDataSourceAutoConfiguration {

    @Bean
    public DynamicDataSourceProvider dynamicDataSourceProvider(StringEncryptor stringEncryptor,
                                                        DataSourceProperties properties) {
        return new JdbcDynamicDataSourceProvider(properties,stringEncryptor);
    }


    @Bean
    public LastParamDsProcessor lastParamDsProcessor(){
        return new LastParamDsProcessor();
    }





}
