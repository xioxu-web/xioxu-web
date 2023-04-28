package com.orc.rpc.client.balance;

import com.orc.rpc.common.model.Service;

import java.util.List;

/**
 * 负载均衡算法接口
 * @author xiaoxu123
 */
public interface LoadBalance {

    /**
     * @param services
     * @return
     */
    Service selectOne(List<Service> services);
}
