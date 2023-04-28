package com.example.middleware_design.Controller;

import com.example.middleware_design.annotation.GPHystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author xiaoxu123
 */
@RestController
public class ServerController {
    private final static Random random = new Random();
    private final Environment environment;

    public ServerController(Environment environment){
       this.environment=environment;
    }

    public String getPort() {
        return environment.getProperty("local.server.port");
    }

    @HystrixCommand(
            fallbackMethod = "errorContent",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "100")
            }
    )

    /**
     * 高级版本
     *
     * @param message
     * @return
     * @throws Exception
     */
    @GPHystrixCommand(timeout = 100,semaphore = 5,fallback ="errorContent")
    @GetMapping("/sayLevel4")
    public String sayLevel4(@RequestParam("message") String message) throws Exception {
        return await(message);
    }

    @GetMapping("/errorContent")
    public String errorContent(String message) {
        return "Fault";
    }

    private String await(String message) throws InterruptedException {
        int value = random.nextInt(200);
        System.out.println("say() cost " + value + "ms");
        Thread.sleep(value);
        System.out.println("port:" + getPort() + ",接收到消息-say:" + message);
        return "port:" + getPort() + ",Hello," + message;
    }
    

}
