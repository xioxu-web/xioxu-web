package com.xxl.job.core.discovery;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The nacos discovery client
 * @author YIJIUE
 */
public class NacosDiscoveryProcessor implements DiscoveryProcessor {

    private static Logger logger = LoggerFactory.getLogger(NacosDiscoveryProcessor.class);

    @NacosInjected
    private NamingService namingService;

    @Override
    public List<String> getServerAddressList(String appName) {
        try {
            final List<Instance> instances = namingService.getAllInstances(appName, true);
            if (instances != null) {
                return instances.stream().map(instance ->
                        instance.getIp() + ":" + instance.getPort()).collect(Collectors.toList());
            }
            logger.error("nacos service does not exist -> {}", appName);
        } catch (NacosException e) {
            logger.error("nacos service discovery fail | appName : {} | error : {}", appName, e.getMessage());
        }
        return new ArrayList<>();
    }
}
