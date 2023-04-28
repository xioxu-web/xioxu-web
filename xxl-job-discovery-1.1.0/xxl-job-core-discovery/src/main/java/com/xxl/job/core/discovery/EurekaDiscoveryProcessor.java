package com.xxl.job.core.discovery;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.LookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The eureka discovery client
 * @author YIJIUE
 */
public class EurekaDiscoveryProcessor implements DiscoveryProcessor {

    private static Logger logger = LoggerFactory.getLogger(EurekaDiscoveryProcessor.class);

    private LookupService<InstanceInfo> lookupService;

    public EurekaDiscoveryProcessor(LookupService<InstanceInfo> lookupService) {
        this.lookupService = lookupService;
    }

    @Override
    public List<String> getServerAddressList(String appName) {
        try {
            Application application = lookupService.getApplication(appName);
            if (application != null) {
                List<InstanceInfo> instances = application.getInstances();
                List<String> serviceList = new ArrayList<>();
                instances.forEach(instanceInfo -> {
                    String service = instanceInfo.getIPAddr() + ":" + instanceInfo.getPort();
                    serviceList.add(service);
                });
                return serviceList;
            }
            logger.error("eureka service does not exist -> {}", appName);
        } catch (Exception e) {
            logger.error("eureka service discovery fail | appName : {} | error : {}", appName, e.getMessage());
        }

        return new ArrayList<>();
    }
}
