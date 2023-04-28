package com.example.demo.localCache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author xiaoxu123
 * 手动加载数据
 */
public class CaffeineManualTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 使用executor设置线程池
        AsyncLoadingCache<Integer, Integer> asyncCache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100).executor(Executors.newSingleThreadExecutor()).buildAsync(CaffeineManualTest::getInDB);

        Integer key=1;
        CompletableFuture<Integer> future = asyncCache.get( key, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                // 执行所在的线程不在是main，而是ForkJoinPool线程池提供的线程
                System.out.println("当前所在线程：" + Thread.currentThread().getName());
                int value = getInDB(key);
                return value;
            }
        } );

        int value = future.get();
        System.out.println("当前所在线程：" + Thread.currentThread().getName());
        System.out.println(value);
    }

    /**
     * 模拟从数据库中读取key
     *
     * @param key
     * @return
     */
    private static int getInDB(int key) {
        return key + 1;
    }
}
