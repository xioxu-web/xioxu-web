package com.orc.rpc.server.register;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认注册服务器
 * @author xiaoxu123
 */
public abstract class DefaultServerRegister implements ServerRegister{

    private Map<String,ServiceObject> serviceMap=new HashMap<>();

    protected String protocol;
    protected Integer port;
    /**
     * 权重
     */
    protected Integer weight;

    @Override
    public void register(ServiceObject so) throws Exception {
        if (so == null){
            throw new IllegalArgumentException("parameter cannot be empty");
        }
        serviceMap.put(so.getName(),so);
    }

    @Override
    public ServiceObject getServiceObject(String name) throws Exception {
        return serviceMap.get(name);
    }



}
