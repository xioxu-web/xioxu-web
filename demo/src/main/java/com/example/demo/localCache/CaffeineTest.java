package com.example.demo.localCache;

import com.github.benmanes.caffeine.cache  .Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaoxu123
 */
public class CaffeineTest {

    public static void main(String[] args) {
        Cache<String, String> loadingCache = Caffeine.newBuilder()
                .initialCapacity(10)
                .maximumSize(1024)
                .expireAfterWrite(10,TimeUnit.SECONDS)
                .build();

        loadingCache.put("key","caffeine本地缓存");

        //删除key
        loadingCache.invalidate( "key");

        // 获取value的值，如果key不存在，获取value后再返回
        String value = loadingCache.getIfPresent("key");

        System.out.println(value);


    }

    private static String getValueFromDB(String key) {
        return "caffeine本地缓存";
    }

}


