package com.example.springdataelastic.poll_algorithm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xiaoxu123
 * @deprecated 平滑加权轮询实现
 */
public class Servers {

    public static  Map<String,Integer> WEIGHT_SERVERS= new LinkedHashMap<>();

    static{
        WEIGHT_SERVERS.put("44.120.110.001:8080",3);
        WEIGHT_SERVERS.put("44.120.110.002:8081",2);
        WEIGHT_SERVERS.put("44.120.110.003:8082",1);
    }

}
//权重类
class Weight{
    //节点信息
    private String server;

    //节点权重值
    private Integer weight;

    //动态权重值
    private Integer currentWeight;

    public Weight() {
    }

    //构造方法
    public Weight(String server, Integer weight, Integer currentWeight) {
        this.server = server;
        this.weight = weight;
        this.currentWeight = currentWeight;
    }

    //封装方法
    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Integer currentWeight) {
        this.currentWeight = currentWeight;
    }
}

class RoundRobinWeight{
   //初始化存储每个节点的权重容器
    private  static Map<String,Weight> weightMap =new HashMap<String,Weight>();

    //计算总权重值
    private static int weightTotal=0;

    static{
       sumWeightTotal();
    }
    //求和总权重值
    public static  void sumWeightTotal(){
        for (Integer weight:Servers.WEIGHT_SERVERS.values()) {
             weightTotal+=weight;
        }
    }

    //获取处理本次请求的具体服务器IP
    public static String getServer() {
        // 判断权重容器中是否有节点信息
        if (weightMap.isEmpty()) {
            //如果没有则将配置的权重服务器列表挨个载入容器
            Servers.WEIGHT_SERVERS.forEach( (servers, weight) -> {
                // 初始化时，每个节点的动态权重值都为0
                weightMap.put( servers, new Weight( servers, weight, 0 ) );
            } );
        }

        // 每次请求时，更改动态权重值
        for (Weight weight:weightMap.values()) {
           weight.setCurrentWeight(weight.getCurrentWeight()+weight.getWeight());
        }

        // 判断权重容器中最大的动态权重值
        Weight maxCurrentWeight=null;
        for(Weight weight:weightMap.values()){
            if(maxCurrentWeight==null || weight.getCurrentWeight()>maxCurrentWeight.getCurrentWeight()){
               maxCurrentWeight=weight;
            }
        }

        // 最后用最大的动态权重值减去所有节点的总权重值
        maxCurrentWeight.setCurrentWeight( maxCurrentWeight.getCurrentWeight()-weightTotal);

        // 返回最大的动态权重值对应的节点IP
        return maxCurrentWeight.getServer();
    }

    public static void main(String[] args) {
        //使用模拟循环请求6次
        for (int i = 0; i <6 ; i++) {
            System.out.println("第"+ i + "个请求：" + getServer());
        }
    }


}
