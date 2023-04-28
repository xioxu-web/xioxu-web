package com.example.springboot_demo.Service;

import com.example.springboot_demo.utils.JedisUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 守护线程类
 * @author xiaoxu123
 */
@Service
@Getter
@Setter
public class DaemonThread implements Runnable{
    private String key;

    private long aTime;

    private long bTime;

    @Override
    public void run() {
    while(true){
        try {
            TimeUnit.MILLISECONDS.sleep(aTime);
            System.out.println("守护线程续命时间"+bTime+"毫秒");
            JedisUtil.jedis.expire(this.key,(int)bTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    }
}
