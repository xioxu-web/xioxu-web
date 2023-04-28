package com.spring.demo.test.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiaoxu123
 * 线程池
 */
public class ThreadPoolTest {

    /**
     * 获取当前的CPU核心数
     */
    private final static  int CPU_COUNT=Runtime.getRuntime().availableProcessors();
    /**
     * 核心线程数
     */
    private final static int CORE_POOL_SIZE=Math.max(2,Math.min(CPU_COUNT-1,4));
    /**
     * 最大线程数
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    /**
     * 非核心线程最大闲置时间
     */
    private static final int KEEP_ALIVE_SECONDS = 30;

    /**
     *线程工厂
     * 用于创建线程
     */
    private static final ThreadFactory sThreadFactory=new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger();

        @Override
        public
        Thread newThread(Runnable r) {
            return new Thread( r, "AsyncTask #" + mCount.getAndIncrement() );
        }

    };

    /**
     * 线程池任务队列
     * 最多可以容纳 128 个可执行的任务
     */
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(30);

    /**
     * 并行执行任务的线程池执行者
     */
    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;


    static{
        /*
            在静态代码块中初始化线程池
            在构造函数中对线程池进行配置 , 配置内容包括 :
            核心线程数
            最大线程数
            非核心线程最大限制时间
            闲置时间的时间单位
            线程池任务队列
            线程创建工厂
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor( CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory );
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        THREAD_POOL_EXECUTOR=threadPoolExecutor;
    }

    static class Task implements Runnable {
        /**
         * 记录线程的索引 0 ~ 99
         */
        private int i = 0;
        public Task(int i) {
            this.i = i;
        }
        @Override
        public void run() {
            System.out.println("线程 ID : " + Thread.currentThread().getName() + " , 线程索引 : " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        THREAD_POOL_EXECUTOR.setRejectedExecutionHandler( (runnable, threadPoolExecutor) -> System.out.println("任务被拒绝......") );
        for (int i = 0; i <36; i++) {
            THREAD_POOL_EXECUTOR.execute(new Task(i));
        }
    }
}
