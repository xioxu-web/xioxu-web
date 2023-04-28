package com.zookeeper.demo.zk.server;
import org.apache.zookeeper.*;


/**
 * @author xiaoxu123
 * 服务端向zookeeper注册、动态上下线
 */
public class DistributeServer {
    private ZooKeeper zk;

    //获取zk连接时，要连接的zk单机版信息
    private String connectString = "127.0.0.1:2181";

    //zk服务端与客户端连接的最大超时时间
    private int sessionTimeout = 30000;


    private void getConnection() throws Exception {
        zk=new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });



    }
}
