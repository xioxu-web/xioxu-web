package com.orc.rpc.client.balance;

import com.orc.rpc.annotation.LoadBalanceAno;
import com.orc.rpc.common.constants.RpcConstant;
import com.orc.rpc.common.model.Service;

import java.util.List;
import java.util.Random;

/**
 * 随机算法
 * @author xiaoxu123
 */
@LoadBalanceAno(RpcConstant.BALANCE_RANDOM)
public class RandomBalance implements LoadBalance{
    private Random random=new Random();

    @Override
    public Service selectOne(List<Service> services) {
        return services.get( random.nextInt( services.size() ) );
    }

}
