package com.example.springboot_demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_demo.Domain.User;
import com.example.springboot_demo.Service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
  private Logger logger = LoggerFactory.getLogger(UserController.class);
  @Autowired
  private UserInfoService userInfoService;
 /* @Autowired
  private RedisTemplate redisTemplate;

  @GetMapping("/selectAllUser")
  public List selectAllUser() {
    List userlist = new ArrayList();
    List user = redisTemplate.opsForList().range("user", 0, -1);

    if (user != null && user.size() > 0) {
      userlist.addAll(user);
    }
    List<User> list = userInfoService.queryAll();
    redisTemplate.opsForList().leftPushAll("user", list);
    logger.info("UserController#selectAllUser:{}", JSONObject.toJSONString(userlist));
    return userlist;
  }*/

}
