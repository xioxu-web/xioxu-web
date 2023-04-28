package com.orc.rpc.client.network;

import com.orc.rpc.client.balance.LoadBalance;
import com.orc.rpc.client.cache.ServerDiscoveryCache;
import com.orc.rpc.client.discovery.ServerDiscovery;
import com.orc.rpc.common.constants.RpcStatusEnum;
import com.orc.rpc.common.model.RpcRequest;
import com.orc.rpc.common.model.RpcResponse;
import com.orc.rpc.common.model.Service;
import com.orc.rpc.common.protocol.MessageProtocol;
import com.orc.rpc.exception.RpcException;
import org.springframework.beans.propertyeditors.ClassArrayEditor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 客户端代理工厂：用于创建远程服务代理类
 *  * 封装编组请求、请求发送、编组响应等操作
 * @author xiaoxu123
 */
public class ClientProxyFactory {
    private ServerDiscovery serverDiscovery;

    private NetWorkClient netWorkClient;

    private Map<String, MessageProtocol> supportMessageProtocols;

    private Map<Class<?>, Object> objectCache = new HashMap<>();

    private LoadBalance loadBalance;

    /**
     * 通过java动态代理类获取代理服务类
     */
    public <T> T getProxy(Class<T> clazz){
       return (T) objectCache.computeIfAbsent(clazz,clz->
                        Proxy.newProxyInstance(clz.getClassLoader(),new Class[]{clz},new ClientInvocationHandler(clz))
                );
    }

    private class ClientInvocationHandler implements InvocationHandler{
        private Class<?> clazz;

        public ClientInvocationHandler(Class<?> clazz){
          this.clazz=clazz;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 通过负载均衡器选取一个服务提供方地址
            if ("toString".equals( method.getName() )) {
                return proxy.toString();
            }
            if("hashCode".equals( method.getName() )){
                return 0;
            }
            //获取服务信息
            String serviceName = clazz.getName();
            List<Service> services = getServiceList( serviceName );
            Service service = loadBalance.selectOne( services );
            //构造request对象
            RpcRequest request = new RpcRequest();
            request.setRequestId( UUID.randomUUID().toString());
            request.setMethod( method.getName());
            request.setServiceName(service.getName());
            request.setParameters(args);
            request.setParameterTypes( method.getParameterTypes());
            // 协议层编组
            MessageProtocol messageProtocol = supportMessageProtocols.get( service.getProtocol() );
            RpcResponse response = netWorkClient.sendRequest( request, service, messageProtocol );
            if (response.getRpcStatus()== RpcStatusEnum.SUCCESS) {
                return response.getData();
            } else if (response.getException() != null) {
                throw new RpcException(response.getException().getMessage());
            } else {
                throw new RpcException(response.getRpcStatus().getDesc());
            }

        }
    }

    /**
     * 根据服务名获取可用的服务地址列表
     * @param serviceName
     * @return
     */
    public List<Service>  getServiceList(String serviceName){
        List<Service> services;
        synchronized (serviceName){
         if(ServerDiscoveryCache.isEmpty( serviceName )){
            services= serverDiscovery.findServiceList( serviceName );
            if(services==null || services.size()>0){
              throw new RpcException("No provider available!");
            }
             ServerDiscoveryCache.put(serviceName, services);
         }else{
             services = ServerDiscoveryCache.get(serviceName);
         }

        }
        return services;
    }


    public LoadBalance getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(LoadBalance loadBalance) {
        this.loadBalance = loadBalance;
    }

    public ServerDiscovery getServerDiscovery() {
        return serverDiscovery;
    }

    public void setServerDiscovery(ServerDiscovery serverDiscovery) {
        this.serverDiscovery = serverDiscovery;
    }

    public NetWorkClient getNetWorkClient() {
        return netWorkClient;
    }

    public void setNetWorkClient(NetWorkClient netWorkClient) {
        this.netWorkClient = netWorkClient;
    }

    public Map<String, MessageProtocol> getSupportMessageProtocols() {
        return supportMessageProtocols;
    }

    public void setSupportMessageProtocols(Map<String, MessageProtocol> supportMessageProtocols) {
        this.supportMessageProtocols = supportMessageProtocols;
    }

    public Map<Class<?>, Object> getObjectCache() {
        return objectCache;
    }

    public void setObjectCache(Map<Class<?>, Object> objectCache) {
        this.objectCache = objectCache;
    }
}

