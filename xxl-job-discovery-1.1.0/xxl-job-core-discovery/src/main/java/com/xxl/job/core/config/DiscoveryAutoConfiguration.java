package com.xxl.job.core.config;

import com.netflix.discovery.EurekaClient;
import com.xxl.job.core.condition.EurekaPropertyCondition;
import com.xxl.job.core.condition.NacosPropertyCondition;
import com.xxl.job.core.discovery.DiscoveryProcessor;
import com.xxl.job.core.discovery.EurekaDiscoveryProcessor;
import com.xxl.job.core.discovery.NacosDiscoveryProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

/**
 * registry center auto configuration class
 * @author YIJIUE
 */
public class DiscoveryAutoConfiguration {

    @Bean
    @Conditional(value = EurekaPropertyCondition.class)
    public DiscoveryProcessor initEureka(EurekaClient eurekaClient) {
        return new EurekaDiscoveryProcessor(eurekaClient);
    }

    @Bean
    @Conditional(value = NacosPropertyCondition.class)
    public DiscoveryProcessor initNacos() {
        return new NacosDiscoveryProcessor();
    }
}
