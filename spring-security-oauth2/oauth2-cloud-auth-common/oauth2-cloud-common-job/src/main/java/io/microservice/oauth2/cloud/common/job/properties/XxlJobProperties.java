package io.microservice.oauth2.cloud.common.job.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author xiaoxu123
 */
@Data
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

    @NestedConfigurationProperty
    private XxlAdminProperties properties=new XxlAdminProperties();

    @NestedConfigurationProperty
    private XxlExecutorProperties executorProperties=new XxlExecutorProperties();
}
