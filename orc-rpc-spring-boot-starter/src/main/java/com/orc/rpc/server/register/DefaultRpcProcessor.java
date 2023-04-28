package com.orc.rpc.server.register;
import com.orc.rpc.annotation.InjectService;
import com.orc.rpc.annotation.Service;
import com.orc.rpc.client.cache.ServerDiscoveryCache;
import com.orc.rpc.client.discovery.ZkChildListenerImpl;
import com.orc.rpc.client.discovery.ZookeeperServerDiscovery;
import com.orc.rpc.client.network.ClientProxyFactory;
import com.orc.rpc.common.constants.RpcConstant;
import com.orc.rpc.server.RpcServer;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

/**
 * * Rpc处理者，支持服务启动暴露，自动注入Service
 * @author xiaoxu123
 */
public class DefaultRpcProcessor implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger logger = LoggerFactory.getLogger(DefaultRpcProcessor.class);


    private ClientProxyFactory clientProxyFactory;

    private ServerRegister serverRegister;

    private RpcServer rpcServer;


    public DefaultRpcProcessor(ClientProxyFactory clientProxyFactory, ServerRegister serverRegister, RpcServer rpcServer) {
        this.clientProxyFactory = clientProxyFactory;
        this.serverRegister = serverRegister;
        this.rpcServer = rpcServer;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Spring启动完毕过后会收到一个事件通知
        if (Objects.isNull(event.getApplicationContext().getParent())){
            ApplicationContext context = event.getApplicationContext();
            // 开启服务
            try {
                startServer(context);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 注入Service
            injectService(context);
        }
    }

    private void injectService(ApplicationContext context) {
        String[] names = context.getBeanDefinitionNames();
        for(String name : names){
            Class<?> clazz = context.getType(name);
            if (Objects.isNull(clazz)){
                continue;
            }

            Field[] declaredFields = clazz.getDeclaredFields();
            for(Field field : declaredFields){
                // 找出标记了InjectService注解的属性
                InjectService injectService = field.getAnnotation(InjectService.class);
                if (injectService == null){
                    continue;
                }

                Class<?> fieldClass = field.getType();
                Object object = context.getBean(name);
                field.setAccessible(true);
                try {
                    field.set(object,clientProxyFactory.getProxy(fieldClass));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                ServerDiscoveryCache.SERVICE_CLASS_NAMES.add(fieldClass.getName());
            }
        }
        // 注册子节点监听
        if (clientProxyFactory.getServerDiscovery() instanceof ZookeeperServerDiscovery){
            ZookeeperServerDiscovery serverDiscovery = (ZookeeperServerDiscovery) clientProxyFactory.getServerDiscovery();
            ZkClient zkClient = serverDiscovery.getZkClient();
            ServerDiscoveryCache.SERVICE_CLASS_NAMES.forEach(name ->{
                String servicePath = RpcConstant.ZK_SERVICE_PATH + RpcConstant.PATH_DELIMITER + name + "/service";
                zkClient.subscribeChildChanges(servicePath, new ZkChildListenerImpl());
            });
            logger.info("subscribe service zk node successfully");
        }

    }

    private void startServer(ApplicationContext context) throws InterruptedException {
        Map<String, Object> beans = context.getBeansWithAnnotation(Service.class);
        if (beans.size() > 0){
            boolean startServerFlag = true;
            for(Object obj : beans.values()){
                try {
                    Class<?> clazz = obj.getClass();
                    Class<?>[] interfaces = clazz.getInterfaces();
                    ServiceObject so = null;
                    /**
                     * 如果只实现了一个接口就用父类的className作为服务名
                     * 如果该类实现了多个接口，则用注解里的value作为服务名
                     */
                    if (interfaces.length != 1){
                        Service service = clazz.getAnnotation(Service.class);
                        String value = service.value();
                        if (value.equals("")){
                            startServerFlag = false;
                            throw new UnsupportedOperationException("The exposed interface is not specific with '" + obj.getClass().getName() + "'");
                        }
                        so = new ServiceObject(value,Class.forName(value),obj);
                    }else {
                        Class<?> supperClass = interfaces[0];
                        so = new ServiceObject(supperClass.getName(),supperClass,obj);
                    }
                    serverRegister.register(so);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            if (startServerFlag){
                rpcServer.start();
            }
        }

    }
}
