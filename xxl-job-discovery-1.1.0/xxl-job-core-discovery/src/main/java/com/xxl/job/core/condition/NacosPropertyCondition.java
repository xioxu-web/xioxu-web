package com.xxl.job.core.condition;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * The Nacos auto configuration condition match
 * @author YIJIUE
 */
public class NacosPropertyCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();
        String nacosProperty = environment.getProperty("nacos.discovery.server-addr");
        return StringUtils.isNotEmpty(nacosProperty);
    }

}
