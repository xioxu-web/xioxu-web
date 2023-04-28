package com.orc.rpc.server.register;

/**
 * 服务注册器
 * @author xiaoxu123
 */
public interface ServerRegister {

    void register(ServiceObject ob) throws Exception;

    ServiceObject getServiceObject(String name)throws Exception;
}
