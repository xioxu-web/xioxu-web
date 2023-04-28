package com.example.lottery.test.test;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApiTest {
  private Logger logger = (Logger) LoggerFactory.getLogger(ApiTest.class);

 /*  @Reference(interfaceClass = IActivityBooth.class,url = "dubbo://127.0.0.1:20880")
   private IActivityBooth iActivityBooth;

   @Test
   public void test_rpc() {
     ActivityReq req = new ActivityReq();
     req.setActivityId(100001L);
     ActivityRes activityRes = iActivityBooth.queryActivityById(req);
     logger.info("测试结果: {}", JSON.toJSONString(activityRes));
   }*/
}
