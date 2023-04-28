package com.concurrency.project.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoxu123
 * @deprecated 线程同步工具类
 */
public class Test {

    /**
     * 网站总访问量：volatile保证线程可见性,保证多线程之间每次获取到的count是最新值
     */

   volatile static int count=0;

    // 模拟访问的方法
    public static void request() throws InterruptedException {
        // 模拟耗时5毫秒
        TimeUnit.MILLISECONDS.sleep( 5 );
        //count ++;
        int expectCount; // 表示期望值
        // 比较并交换
        while (!compareAndSwap( (expectCount = getCount()), expectCount + 1 )) {
        }
    }



   public static synchronized Boolean compareAndSwap(int expectCount, int newCount){
       // 判断count当前值是否和期望值expectCount一致，如果一致 将newCount赋值给count
      if(getCount()==expectCount){
         count=newCount;
         return true;
      }
        return false;
   }

   public static int getCount(){
       return count;
   }



    public static void main(String[] args) throws Exception{
       //开始时间
        long startTime = System.currentTimeMillis();
        int threadSize =100;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);

        for (int i = 0; i <threadSize ; i++) {

            Thread thread = new Thread( new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        try {
                            request();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            countDownLatch.countDown();
                        }

                    }
                }

            } );

            thread.start();
        }
            countDownLatch.await();
            long endTime  = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + ",耗时：" + (endTime - startTime) + ", count = " + count); h
    }

}
