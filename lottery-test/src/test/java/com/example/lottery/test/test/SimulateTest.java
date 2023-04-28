package com.example.lottery.test.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SimulateTest {

    @Test
    public void test() {
        //并发的线程数
        int threadSize = 100;
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadSize);
        for (int i = 0; i < threadSize; i++) {
            fixedThreadPool.submit(() -> {
                RestTemplate restTemplate = new RestTemplate();
                String result = restTemplate.getForObject("http://localhost:8086/web/order/deduct_stock/1", String.class);
                System.out.println(result);
            });
        }
    }
}
