package com.distributed.demo.handler.utils;

import org.springframework.cglib.proxy.InvocationHandler;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Method;

/**
 * @author xiaoxu123
 */
public class JedisHandler implements InvocationHandler {

    private JedisPool jedisPool;
    private static final String EXCEPTION_METHOD = "close";

    public JedisHandler(JedisPool jedisPool){
        this.jedisPool = jedisPool;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (EXCEPTION_METHOD.equals(method.getName())) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Object invoke = method.invoke(jedis, args);
            return invoke;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}