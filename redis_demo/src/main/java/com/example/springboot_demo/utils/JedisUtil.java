package com.example.springboot_demo.utils;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * @author xiaoxu123
 */
@Component
public class JedisUtil {

    public static Jedis jedis;
    static{
        jedis=new Jedis("127.0.0.1",6379);
    }

}
