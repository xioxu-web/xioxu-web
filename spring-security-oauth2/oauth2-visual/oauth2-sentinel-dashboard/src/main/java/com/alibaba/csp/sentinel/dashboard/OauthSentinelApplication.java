package com.alibaba.csp.sentinel.dashboard;

import com.alibaba.csp.sentinel.init.InitExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xiaoxu123
 */
@SpringBootApplication
public class OauthSentinelApplication {

    public static void main(String[] args) {
      triggerSentinelInit();
      SpringApplication.run(OauthSentinelApplication.class,args);
    }

    private static void triggerSentinelInit(){
      new Thread(()-> InitExecutor.doInit()).start();
    }

}
