package com.xxl.job.core.discovery;

import java.util.List;

/**
 * @author YIJIUE
 */
public interface DiscoveryProcessor {

    List<String> getServerAddressList(String appName);

}
