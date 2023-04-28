package io.microservices.shop.service.user.controller;

import com.alibaba.fastjson.JSON;
import io.microservices.shop.common.constant.StatusCode;
import io.microservices.shop.common.resp.Result;
import io.microservices.shop.common.utils.JwtUtil;
import io.microservices.shop.service.user.api.domain.User;
import io.microservices.shop.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author xiaoxu123
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public Result login(String username, String password, HttpServletResponse response, HttpServletRequest request) {
        // 查询用户信息
        User user = userService.findById(username);
        // 对比密码, 将明文密码加密后对比数据库中的加密密码
        if (BCrypt.checkpw(password, user.getPassword())) {
            // 创建用户令牌信息
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("role","USER");
            tokenMap.put("success","SUCCESS");
            tokenMap.put("username",username);
            // 生成令牌
            String token = JwtUtil.createJWT(UUID.randomUUID().toString(), JSON.toJSONString(tokenMap), null);
            // 将令牌信息存入 Cookie 中
            Cookie cookie = new Cookie("Authorization", token);
            cookie.setDomain("localhost");
            cookie.setPath("/");
            response.addCookie(cookie);
            // 密码匹配，登陆成功
            return new Result(true, StatusCode.OK, "登陆成功!", token);
        }

        // 密码匹配失败，登陆失败
        return new Result(false, StatusCode.LOGINERROR, "账号或密码有误!");
    }

    /***
     * 根据ID查询User数据
     * @param id
     * @return
     */
    @GetMapping({"/{id}","/load/{id}"})
    public Result<User> findById(@PathVariable String id) {
        // 调用UserService实现根据主键查询User
        User user = userService.findById(id);
        return new Result<User>(true, StatusCode.OK, "查询成功", user);
    }
}
