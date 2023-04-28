package com.zookeeper.demo.zk.config;
import com.zookeeper.demo.zk.service.Impl.ZkServiceRegistryImpl;
import com.zookeeper.demo.zk.service.ZkServiceRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @author xiaoxu123
 */
@Configuration
public class RegistryConfig {

    @Value("${registry.zk.servers}")
    private String servers;

    @Value("${server.port}")
    private int serverPort;

    @Value("${spring.application.name}")
    private String serviceName;


    @Bean
    public ZkServiceRegistry serviceRegistry() {
        ZkServiceRegistry serverRegistry = new ZkServiceRegistryImpl(servers);
        String serviceAdress = getServiceAddress();
        serverRegistry.register(serviceName, serviceAdress);
        return serverRegistry;
    }

    private String getServiceAddress() {
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
        }
        String ip = localHost.getHostAddress();

        return ip + ":" + serverPort;
    }
}
