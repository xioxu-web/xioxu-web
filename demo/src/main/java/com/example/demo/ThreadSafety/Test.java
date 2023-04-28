package com.example.demo.ThreadSafety;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author xiaoxu123
 */
@Slf4j
public class Test {

    public static void main(String[] args) throws Exception {
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = getData( 100 - 10 );

        //初始化900个元素
        log.info( "init size{}", concurrentHashMap.size() );

        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        //使用线程池并发处理逻辑
        forkJoinPool.execute( () ->
                IntStream.rangeClosed( 1, 10 )
                        .parallel()
                        .forEach( i -> {
                            //查询还要补充多少元素
                            int gap = 100 - concurrentHashMap.size();
                            log.info( "gap size :{}", gap );
                            //补充元素
                            concurrentHashMap.putAll( getData( gap ) );
                        } ) );
        //等待所有任务完成
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination( 1, TimeUnit.HOURS );

        log.info( "finesh size:{}", concurrentHashMap.size() );

    }

    private static ConcurrentHashMap<Integer, Integer> getData(int TIME_COUNT) {
        Random rd = new Random();
        ConcurrentHashMap<Integer, Integer> hs = new ConcurrentHashMap<Integer, Integer>();
        int num = 0;
        int j = 0;
        Integer count;
        for (int i = 0; i <= 100; i++) {
            j++;
            num = rd.nextInt( 100 );
            count = hs.get( num );
            if (hs.get( num ) != null) {
                count++;
            } else {
                count = 1;
            }
            hs.put( num, count );

            System.out.println( num + ":" + hs.get( num ) );
        }
        //map按值排序
        List<Map.Entry<Integer, Integer>> list = new ArrayList<Map.Entry<Integer, Integer>>(
                hs.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public
            int compare(Map.Entry<Integer, Integer> o1,
                        Map.Entry<Integer, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        } );
        for (Map.Entry<Integer, Integer> m : list) {
            System.out.println( m.getKey() + "-" + m.getValue() );
        }

        return hs;


    }
}