package com.distributed.demo.handler.utils;
import com.distributed.demo.handler.config.RedisProperties;
import com.distributed.demo.handler.model.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * @author xiaoxu123
 */
@Component
public class RedisUtil{

    /**
     * 静态注入JedisPool连接池
     */
    private static JedisPool jedisPool;

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        RedisUtil.jedisPool= jedisPool;
    }

    //Redis服务器IP
    private static String ADDR ="127.0.0.1";

    //Redis的端口号
    private static int PORT = 6379;

    //访问密码
    private static String AUTH =  "";

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 500;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 50;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 100000;

    private static int TIMEOUT = 10000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;


    public RedisUtil(JedisPool jedisPool) {
        this.setJedisPool(jedisPool);
    }

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            if (StringUtils.isNotBlank(AUTH)) {
                jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
            } else {
                System.out.println("redis host:" + ADDR);
                jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取Jedis实例
     * @param
     * @return redis.clients.jedis.Jedis

     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis jedis = jedisPool.getResource();
                return jedis;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("获取Jedis资源异常:" + e.getMessage());
        }
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }


    public static void main(String[] args) {
        Jedis jedis = jedisPool.getResource();
        jedis.set("jedis","远程数据库");
        System.out.println(jedis.get("jedis"));
    }

}