package com.orc.rpc.client.discovery;

import com.orc.rpc.common.model.Service;

import java.util.List;

/**
 * 服务发现抽象类
 * @author xiaoxu123
 */
public interface ServerDiscovery {

    List<Service> findServiceList(String name);
}
