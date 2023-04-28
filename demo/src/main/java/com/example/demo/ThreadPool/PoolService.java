package com.example.demo.ThreadPool;

import javafx.concurrent.Task;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiaoxu123
 * 自定义线程池
 */
public class PoolService {

    public static void main(String[] args) {
        //创建线程池,使用自定义的线程池
        ExecutorService executorService=new CustomThreadPoolExecutor(1, 1, 100, TimeUnit.SECONDS,new ArrayBlockingQueue<>(1),new ThreadFactory(){
            AtomicInteger integer = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "mxsm-"+integer.getAndIncrement());
            }
        });

        for(int i = 0; i < 10; ++i){
            final int b = i;
            executorService.execute(() -> {
                for (;;){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println(Thread.currentThread().getName()+ b +" 当前时间："+System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        System.out.println("主线程结束");

    }
  /*  public  static class  Text implements  Runnable
    {
        public  int x;
        public  int y;
        public  Text(int x,int y)
        {
            this.x=x;
            this.y=y;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"线程x/y结果的为"+x+"/"+y+"="+(x/y));
        }
    }*/

    }
    class CustomThreadPoolExecutor extends ThreadPoolExecutor {

        //构造器
        public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                        TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super( corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,threadFactory);
        }


        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r,t);
            if (t!= null) {
                System.out.println("afterExecute里面获取到异常信息，处理异常" + t.getMessage());
            }
          /*  //如果r的实际类型是FutureTask 那么是submit提交的，所以可以在里面get到异常
            if (r instanceof FutureTask) {
                FutureTask<?> futureTask = (FutureTask<?>) r;
                try {
                    futureTask.get();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("线程池submit提交,异常处理:" + e );
                }
            }*/
        }




    };

