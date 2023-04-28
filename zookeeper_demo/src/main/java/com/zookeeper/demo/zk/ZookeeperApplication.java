package com.zookeeper.demo.zk;
import com.zookeeper.demo.zk.service.Impl.ZkServiceDiscoveryImpl;
import com.zookeeper.demo.zk.service.Impl.ZkServiceRegistryImpl;
import com.zookeeper.demo.zk.service.ZkServiceDiscovery;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZookeeperApplication {
    private static final String SERVICE_NAME = "zookeeper_demo";

    private static final String SERVER_ADDRESS = "127.0.0.1:2181";

    private static final int SESSION_TIMEOUT = 30000;

    public static void main(String[] args) throws KeeperException, InterruptedException {
        SpringApplication.run(ZookeeperApplication.class, args);

        ZkServiceRegistryImpl registry = new ZkServiceRegistryImpl("127.0.0.1:2181");
        registry.register(SERVICE_NAME,SERVER_ADDRESS);
        ZkServiceDiscoveryImpl discovery = new ZkServiceDiscoveryImpl();
        discovery.connectServer();
        discovery.discovery(SERVICE_NAME);
        while (true){}

    }

}
