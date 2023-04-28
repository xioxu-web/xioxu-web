package io.microservice.oauth2.cloud.monitor.converter;

import de.codecentric.boot.admin.server.cloud.discovery.DefaultServiceInstanceConverter;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;

/**
 * @author xiaoxu123
 */
@Configuration(proxyBeanMethods = false)
public class NacosServiceInstanceConverter extends DefaultServiceInstanceConverter {


    @Override
    protected Map<String, String> getMetadata(ServiceInstance instance) {
        return (instance.getMetadata()!=null)
                ? instance.getMetadata().entrySet().stream().filter((e)-> e.getKey()!=null && e.getValue()!=null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                : emptyMap();
    }

}
