package cn.iocoder.oauth.module.pay.dal.redis.notify;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import java.util.concurrent.TimeUnit;

import static cn.iocoder.oauth.module.pay.dal.redis.RedisKeyConstants.PAY_NOTIFY_LOCK;

/**
 * @author xiaoxu123
 */
@Repository
public class PayNotifyLockCoreRedisDAO {

    @Resource
    private RedissonClient redissonClient;

    public void lock(Long id, Long timeoutMillis, Runnable runnable){
        String key = formatKey( id );
        RLock lock = redissonClient.getLock( key );
        try {
            lock.lock( timeoutMillis, TimeUnit.MILLISECONDS );
            //执行逻辑
            runnable.run();
        }finally {
            lock.unlock();
        }
    }

    private static String formatKey(Long id) {
        return String.format(PAY_NOTIFY_LOCK.getKeyTemplate(), id);
    }


}
