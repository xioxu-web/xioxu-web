package com.orc.consumer;

import com.orc.api.ApiResult;
import com.orc.api.User;
import com.orc.api.UserService;
import com.orc.rpc.annotation.InjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaoxu123
 */
@RestController
@RequestMapping("test")
public class TestController {

    @InjectService
    private UserService userService;

    @GetMapping("/user")
    public ApiResult<User> getUser(@RequestParam("id")Long id){
        return userService.getUser(id);
    }

}
