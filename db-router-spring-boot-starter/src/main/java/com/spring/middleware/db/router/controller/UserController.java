package com.spring.middleware.db.router.controller;

import com.spring.middleware.db.router.domain.UserInfo;
import com.spring.middleware.db.router.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author xiaoxu123
 */
@RestController
public class UserController {

    @Autowired
    private  UserService userService;

    @RequestMapping("/listUser")
    public List<UserInfo> listUser() {
        List<UserInfo> users = userService.findAll();
        return users;
    }

    @RequestMapping("/insertUser")
    public void insertUser()
    {
        UserInfo user = new UserInfo();
        user.setUsername("xiaoxu");
        user.setPswd("123456");
        userService.insertUser(user);
    }
}
