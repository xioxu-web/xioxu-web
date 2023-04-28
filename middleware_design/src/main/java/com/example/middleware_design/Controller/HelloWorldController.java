package com.example.middleware_design.Controller;

import com.example.middleware_design.Domain.UserInfo;
import com.example.middleware_design.annotation.DoWhiteList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    @DoWhiteList(key = "userId", returnJson = "{\"code\":\"1111\",\"info\":\"非白名单可访问用户拦截！\"}")
    @GetMapping(path = "/api/queryUserInfo")
    public UserInfo queryUserInfo(@RequestParam String userId) {
        return new UserInfo("用户Id:" + userId, "xiaoxu", 24, "杭州市余杭区古墩路奥克斯缔逸城");
    }
}
