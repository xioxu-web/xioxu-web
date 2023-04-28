package com.example.springboot_demo.Service;
import com.example.springboot_demo.utils.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.params.SetParams;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Redis分布式锁工具类
 * @author xiaoxu123
 */
@Service
public class RedisDistributedLockTool {

    /**
     *加锁成功
     */
    private static final String LOCK_SUCCESS="ok";

    /**
     * 解锁成功
     */
    private static final Long RELEASE_SUCCESS=1L;

    /**
     *如果没有获取到锁，将每10ms重试一次
     */
    private static int DEFAULT_ACQUIRY_RETRY_MILLIS=10;

    /**
     * @Title加锁
     * @Description 如果获取锁失败，每10ms重试一次，重试最大次数3,否则返回false
     * @Param [key,value,expireTime]
     * @return boolean
     */
    public static boolean tryLock(String key,String value,Long expireTime) throws InterruptedException {
        int count = 0;
        int countMax = 3;
        if (count < countMax) {
            SetParams params = new SetParams();
            //NX表示仅在key不存在时才能设置成功
            params.nx();
            params.px(expireTime);
            if (LOCK_SUCCESS.equals(JedisUtil.jedis.set(key, value, params))) {
                return true;
            } else {
                //如果没有拿到锁，每10ms重试一次，最多重试3次
                TimeUnit.MILLISECONDS.sleep(10);
                count++;
            }
        }
        return false;
    }

    /**
     * @Title解锁
     * @Description 解锁时与传进来的value作对比,看是否是自己加的锁,如果是自己加的锁再删除
     * @Param key,value
     * @return boolean
     */
     public static boolean unLock(String key,String value){
       String script="if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
         Object result = JedisUtil.jedis.eval(script, Collections.singletonList(key), Collections.singletonList(value));
         if(RELEASE_SUCCESS.equals(result)){
          return true;
         }
         return false;
     }

}
