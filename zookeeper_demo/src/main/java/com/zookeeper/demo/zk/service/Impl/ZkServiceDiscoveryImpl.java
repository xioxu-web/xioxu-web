package com.zookeeper.demo.zk.service.Impl;

import com.zookeeper.demo.zk.service.ZkServiceDiscovery;
import com.zookeeper.demo.zk.utils.LoadBalanse;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author xiaoxu123
 */
public class ZkServiceDiscoveryImpl implements ZkServiceDiscovery {

    private static final Logger logger = LoggerFactory.getLogger(ZkServiceDiscovery.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private volatile List<String> serviceAddressList = new ArrayList<>();

    private static int ZK_SESSION_TIMEOUT = 30000;

    private static final String BASE_SERVICE = "/registry";

    private static final String SERVICE_NAME = "zookeeper_demo";

    public ZkServiceDiscoveryImpl() {
        ZooKeeper zk = connectServer();
        if (zk != null) {
            watchNode(zk);
        }
    }

    @Override
    public String discovery(String serviceName) throws KeeperException, InterruptedException {
        String data = null;
        int size = serviceAddressList.size();
        if (size > 0) {
            if (size == 1) {
                data = serviceAddressList.get(0);
                logger.info("unique service address : {}",data);
            } else { //使用随机分配法。简单的负载均衡法
                data = serviceAddressList.get(ThreadLocalRandom.current().nextInt(size));
                logger.info("choose an address : {}", data);
            }
        }
        return data;
    }

    /**
     * 连接 zookeeper
     * @return
     */
    public ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper( "127.0.0.1:2181", ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public
                void process(WatchedEvent event) {
                    if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            } );
            latch.await();
        } catch (IOException | InterruptedException e) {
            logger.error( "", e );
        }
        return zk;
    }

    /**
     * 获取服务地址列表
     * @param zk
     */
    private void watchNode(final ZooKeeper zk) {
        List <String> dataList =new ArrayList<>();
        try {
            //获取服务路径
            String servicePath = BASE_SERVICE +"/"+ SERVICE_NAME;
            logger.info("ZkServiceDiscoveryImpl watchNode get {}:"+servicePath);
            //获取服务路径下的地址节点
            List <String> nodeList = zk.getChildren(servicePath,true);
            logger.info("ZkServiceDiscoveryImpl watchNode get {}:"+nodeList);
            int size = nodeList.size();
                //生成地址路径
                String addressPath = servicePath + '/';
                if (size ==1) {
                    // 如果只有一个地址，则获取该地址
                    addressPath =servicePath+nodeList.get(0);
                } else {
                    // 若存在多个地址，则随机获取一个地址
                    addressPath = addressPath + nodeList.get(ThreadLocalRandom.current().nextInt(size));
                }
            logger.info("ZkServiceDiscoveryImpl watchNode get addressPath{}:"+addressPath);
            String[] split = addressPath.split("/");
                if (split.length < 4){
                   return;
               }
            String serviceName = split[2];
            logger.info("ZkServiceDiscoveryImpl watchNode get serviceName {}:"+serviceName);
            byte[] bytes = zk.getData(addressPath,false, null);
            logger.info("ZkServiceDiscoveryImpl watchNode get serviceAddress {}:"+new String(bytes,StandardCharsets.UTF_8));
            dataList.add(new String(bytes));
            LoadBalanse.SERVICE_LIST =dataList;
            this.serviceAddressList=dataList;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
