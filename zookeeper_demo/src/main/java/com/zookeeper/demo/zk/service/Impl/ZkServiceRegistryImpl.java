package com.zookeeper.demo.zk.service.Impl;

import com.zookeeper.demo.zk.config.RegistryConfig;
import com.zookeeper.demo.zk.service.ZkServiceRegistry;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author xiaoxu123
 */
@Service
public class ZkServiceRegistryImpl implements ZkServiceRegistry,Watcher {

    private static final Logger logger = LoggerFactory.getLogger(ZkServiceRegistry.class);

   private static final String REGISTRY_PATH="/registry";

    private static final int SESSION_TIMEOUT = 30000;

    private static CountDownLatch latch=new CountDownLatch(1);

    private ZooKeeper zk;

    public ZkServiceRegistryImpl(){

    }

    public ZkServiceRegistryImpl(String zkServers){
        //创建zookeeper客户端
        try {
            zk = new ZooKeeper(zkServers,SESSION_TIMEOUT, this);
            latch.await();
            logger.info("connected to zookeeper");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("create zookeeper client failure", e);
        }
    }

    @Override
    public void register(String serviceName, String serviceAddress) {
        //创建跟节点
        String registryPath = REGISTRY_PATH;
        try {
            //创建节点,创建
            if (zk.exists(registryPath, false) == null) {
                zk.create(registryPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                logger.info("create registry node :" + registryPath);
            }
            //创建服务节点,持久节点
            String servicePath = registryPath + "/" + serviceName;
            servicePath =  servicePath.replace("//", "/");
            if (zk.exists(servicePath, false) == null) {
                String addressNode = zk.create(servicePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                logger.info("create service node:" + addressNode);
            }
            //创建地址节点
            String addressPath = servicePath + "/address-";
            String addressNode = zk.create(addressPath, serviceAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            logger.info("create address node serviceAddress:" + serviceAddress + " addressNode" + addressNode);
            System.out.println("服务发布成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("create node failure", e);
        }
    }

    /**
     *@param path
     *@param data
     *@throws KeeperException
     *@throws InterruptedException
     */
    public void create(String path,byte[] data)throws KeeperException, InterruptedException{
        /**
         * 此处采用的是CreateMode是PERSISTENT  表示The znode will not be automatically deleted upon client's disconnect.
         * EPHEMERAL 表示The znode will be deleted upon the client's disconnect.
         */
        this.zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     *@param path
     *@throws KeeperException
     *@throws InterruptedException
     */
    public void getChild(String path) throws KeeperException, InterruptedException{
        try{
            List<String> list=this.zk.getChildren(path,false);
            if(list.isEmpty()){
                logger.debug(path+"中没有节点");
            }else{
                logger.debug(path+"中存在节点");
                for(String child:list){
                    logger.debug("节点为："+child);
                }
            }
        }catch (KeeperException.NoNodeException e) {
            // TODO: handle exception
            throw e;

        }
    }

    public byte[] getData(String path) throws KeeperException, InterruptedException {
        return  this.zk.getData(path, false,null);
    }

    @Override
    public void process(WatchedEvent event) {
        //获取事件类型和状态
        Event.EventType type = event.getType();
        Event.KeeperState state = event.getState();
        String path = event.getPath();
        System.out.println("zk状态："+state+"\tzk类型:"+type+"\t操作地址："+path);
        if(state == Event.KeeperState.SyncConnected){
            /**
             * 监听连接状态
             */
            if(type == Event.EventType.None){
                System.out.println("~~~~~~~~~~~~~~~~zk成功建立连接~~~~~~~~~~~~");
                latch.countDown();
            }else if(type == Event.EventType.NodeCreated){
                System.out.println("~~~~~~~~~~~~~~~~zk成功创建节点~~~~~~~~~~~~");
            }else if(type == Event.EventType.NodeDataChanged){
                System.out.println("~~~~~~~~~~~~~~~~zk成功修改节点数据~~~~~~~~~~~~");
            }else if(type == Event.EventType.NodeDeleted){
                System.out.println("~~~~~~~~~~~~~~~~zk成功删除节点数据~~~~~~~~~~~~");
            }else if(type == Event.EventType.NodeChildrenChanged){
                System.out.println("~~~~~~~~~~~~~~~~zk成功修改子节点数据~~~~~~~~~~~~");
            }
        }
    }
}
