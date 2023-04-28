package com.orc.rpc.client.balance;

import com.orc.rpc.common.model.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 轮询算法
 * @author xiaoxu123
 */
public class FullRoundBalance implements LoadBalance{

    private static Logger logger = LoggerFactory.getLogger(FullRoundBalance.class);

    private int index;

    @Override
    public synchronized Service selectOne(List<Service> services) {
        if(services.size()==index){
          index=0;
        }
        return services.get( index++ );
    }
}
