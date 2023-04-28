package com.example.springdataelastic.poll_algorithm;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiaoxu123
 * 最小活跃数算法
 */
//节点类：用于封装集群中的每个节点
public class MinActive {
    private String IP;

    private AtomicInteger active;

    public
    MinActive() {
    }

    public
    MinActive(String IP, int active) {
        this.IP = IP;
        // 将外部传递的活跃数作为默认活跃数
        this.active = new AtomicInteger( active );
    }

    public
    String getIP() {
        // 每分发一个请求时自增一次活跃数
        active.incrementAndGet();
        return IP;
    }

    public
    AtomicInteger getActive() {
        return active;
    }
}
// 集群类：用于模拟集群节点列表
class ClusterServers{
   //活跃度衰减器
   public static void attenuator(){
     new Thread(()->{
         for (MinActive minactive:ClusterServers.SERVERS) {
             if(minactive.getActive().get()!=0){
                 // 则自减一个活跃度
               minactive.getActive().getAndIncrement();
             }
         }
         try {
             TimeUnit.MILLISECONDS.sleep(2000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }).start();
   }

    //模拟的集群节点信息，活跃数最开始默认为0
    public static List<MinActive> SERVERS = Arrays.asList(
            new MinActive("44.120.110.001:8080",0),
            new MinActive("44.120.110.002:8081",0),
            new MinActive("44.120.110.003:8082",0)
    );
}
//最小活跃数算法
class  LeastActive{

    public static String getServer(){
        // 初始化最小活跃数和最小活跃数的节点
        int leastActive  = Integer.MAX_VALUE;
        MinActive leastServer  = new MinActive();
        //遍历集群中所有的节点
        for (MinActive minactive:ClusterServers.SERVERS) {
          //找出活跃数最小的节点
         if(leastActive>minactive.getActive().get()){
             leastActive=minactive.getActive().get();
             leastServer=minactive;
         }
        }

        // 返回活跃数最小的节点IP
        return leastServer.getIP();
    }

    public static void main(String[] args){
        ClusterServers.attenuator();
        for (int i = 1; i <= 20; i++){
            System.out.println("第"+ i + "个请求：" + getServer());
        }
    }
}
