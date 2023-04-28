package com.xxl.job.core.config.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnumerableCompositePropertySource;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.*;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * if nacos address is empty, auto close discovery enabled
 * @author YIJIUE
 * @since 1.1.0
 */
public class NacosPropertiesProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment configurableEnvironment, SpringApplication springApplication) {

        PropertySource<?> applicationConfigurationProperties = configurableEnvironment.getPropertySources().get("applicationConfigurationProperties");
        if (applicationConfigurationProperties != null) {
            Object property = applicationConfigurationProperties.getProperty("nacos.discovery.server-addr");
            if (StringUtils.isEmpty(property)) {

                @SuppressWarnings("unchecked")
                Collection<PropertySource<?>> source = (Collection<PropertySource<?>>) applicationConfigurationProperties.getSource();
                if (source.size() > 0) {
                    for (Object o : source) {
                        if (o instanceof EnumerableCompositePropertySource) {
                            EnumerableCompositePropertySource compositePropertySource = (EnumerableCompositePropertySource) o;
                            for (PropertySource<?> propertySource : compositePropertySource.getSource()) {

                                Properties properties = (Properties) propertySource.getSource();
                                properties.put("nacos.discovery.enabled", "false");
                                configurableEnvironment.getPropertySources().replace("applicationConfigurationProperties",
                                        propertySource);
                            }
                        }
                    }
                }
            }
        }

    }
}
