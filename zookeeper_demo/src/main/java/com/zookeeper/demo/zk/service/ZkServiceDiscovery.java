package com.zookeeper.demo.zk.service;

import org.apache.zookeeper.KeeperException;

/**
 * @author xiaoxu123
 * 服务发现
 */
public interface ZkServiceDiscovery {

    /**
     * 服务发现
     * @param serviceName
     * @return
     */
    //根据服务名称返回服务地址
    String  discovery(String serviceName)throws KeeperException, InterruptedException;
}
