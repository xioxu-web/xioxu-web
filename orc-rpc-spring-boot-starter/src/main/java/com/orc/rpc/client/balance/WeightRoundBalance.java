package com.orc.rpc.client.balance;

import com.orc.rpc.annotation.LoadBalanceAno;
import com.orc.rpc.common.constants.RpcConstant;
import com.orc.rpc.common.model.Service;

import java.util.List;

/**
 * 加权轮询
 * @author xiaoxu123
 */
@LoadBalanceAno(RpcConstant.BALANCE_WEIGHT_ROUND)
public class WeightRoundBalance implements LoadBalance{

    private static int index;

    @Override
    public synchronized Service selectOne(List<Service> services) {
        int allWeight = services.stream().mapToInt( Service::getWeight ).sum();
        int number= (index++) % allWeight;
        for (Service service:services) {
          if(service.getWeight()>number){
             return service;
          }
          number-=service.getWeight();
        }
        return null;
    }

}
