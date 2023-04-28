package com.zookeeper.demo.zk.utils;

import java.util.List;

/**
 * @author xiaoxu123
 */
public abstract class LoadBalanse {
    public volatile static List<String> SERVICE_LIST;
    public abstract String choseServiceHost();
}
