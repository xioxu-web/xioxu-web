package com.example.data.structure;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author xiaoxu123
 */
@Slf4j
public class ThreadSafety {

    public static void main(String[] args) throws Exception {

        ConcurrentHashMap<String,Long> concurrentHashMap=getData(1000-100);

        //初始900个元素
        log.info("")


        ForkJoinPool forkJoinPool = new ForkJoinPool( 10 );
        //使用线程池并发处理逻辑
        forkJoinPool.execute(()-> IntStream
             .rangeClosed(1,10)
             .parallel()
             .forEach(i->{
                 //还需要填充的元素
                 int need=1000-concurrentHashMap.size();
                 concurrentHashMap.putAll(getData(need));
             }));
          forkJoinPool.shutdown();
          forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
          return 1;
    }
}
