package com.example.demo.limit;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiaoxu123
 * 固定窗口算法的实现
 */
public class RateLimiterSimpleWindow {
    // 阈值
    private static Integer QPS = 2;
    // 时间窗口（毫秒）
    private static long TIME_WINDOWS = 1000;
    //计数器
    private static AtomicInteger REQ_COUNT=new AtomicInteger();
    //开始时间
    private static long START_TIEM=System.currentTimeMillis();

    public synchronized  static boolean tryAcquire(){
      if((System.currentTimeMillis()-START_TIEM)>TIME_WINDOWS){
         REQ_COUNT.set(0);
         START_TIEM=System.currentTimeMillis();
      }
      return REQ_COUNT.incrementAndGet()<=QPS;
    }

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(400);
        for (int i = 0; i <10; i++) {
            TimeUnit.MILLISECONDS.sleep(250);
            LocalTime now = LocalTime.now();
            if(!tryAcquire()){
                System.out.println(now+"请求被限流...");
            }else{
                System.out.println(now+"获取用户访问令牌...");
            }
        }
    }

}
