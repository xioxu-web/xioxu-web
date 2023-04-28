package com.xxl.job.core.condition;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * The Nacos auto configuration condition match
 * @author YIJIUE
 */
public class EurekaPropertyCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        final Environment environment = conditionContext.getEnvironment();
        final String eurekaProperty = environment.getProperty("eureka.client.service-url.defaultZone");
        final String humpProperty = environment.getProperty("eureka.client.serviceUrl.defaultZone");
        return StringUtils.isNotEmpty(eurekaProperty) || StringUtils.isNotEmpty(humpProperty);
    }
}
