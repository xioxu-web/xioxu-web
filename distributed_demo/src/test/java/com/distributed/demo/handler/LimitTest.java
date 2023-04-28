package com.distributed.demo.handler;
import com.distributed.demo.handler.limit.TokenBucketLimiterPolicy;
import com.distributed.demo.handler.limit.TokenBucketLimiterStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;


@SpringBootTest(classes = LimitTest.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LimitTest {

    @Test
    public void test() {
        final TokenBucketLimiterStrategy tokenBucketLimiterStrategy = new TokenBucketLimiterStrategy(new TokenBucketLimiterPolicy(20, 1000, 0));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        execute(new Runnable() {
            @Override
            public void run() {
                tokenBucketLimiterStrategy.access("test-lua");
            }
        },20,10);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }



    public static void execute(final Runnable run, int threadSize, int loop) {
        AtomicInteger count = new AtomicInteger();
        for (int j = 0; j < loop; j++) {
            System.out.println( "第" + (j + 1) + "轮并发测试，每轮并发数" + threadSize );
            final CountDownLatch countDownLatch = new CountDownLatch( 1 );
            Set<Thread> threads = new HashSet<>( threadSize );
            // 批量新建线程
            for (int i = 0; i < threadSize; i++) {
                threads.add(
                        new Thread( new Runnable() {
                            @Override
                            public
                            void run() {
                                try {
                                    countDownLatch.await();
                                    run.run();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, "Thread" + count.getAndIncrement() ) );
            }
            // 开启所有线程并确保其进入 Waiting 状态
            for (Thread thread : threads) {
                thread.start();
                while (thread.getState() != Thread.State.WAITING) ;
            }
            // 唤醒所有在 countDownLatch 上等待的线程
            countDownLatch.countDown();
            // 等待所有线程执行完毕，开启下一轮
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void execute(Runnable run) {
        execute( run, 1000, 1 );
    }

    public static void execute(Runnable run, int threadSize) {
        execute( run, threadSize, 1 );
    }






}
