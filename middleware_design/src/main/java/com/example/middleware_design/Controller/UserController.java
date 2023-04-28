package com.example.middleware_design.Controller;

import com.example.middleware_design.Domain.UserInfo;
import com.example.middleware_design.annotation.DoRateLimiter;
import com.example.middleware_design.annotation.RequestLimiter;
import com.example.middleware_design.dto.ResponseDTO;
import com.example.middleware_design.enums.ResponseEnum;
import com.example.middleware_design.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoxu123
 */
@RestController
@RequestMapping("/user")
public class UserController{
    /*@DoRateLimiter(permitsPerSecond =0.5, timeout = 500)
    @RequestMapping(path = "/api/queryUserInfo", method = RequestMethod.GET)
    public String queryUserInfo(HttpServletRequest request) throws InterruptedException {
        UserInfo userInfo = new UserInfo();
        return LocalTime.now() + " " + request.getRequestURI() + "请求成功";
    }*/
    @Autowired
    private UserInfoService userInfoService;

    @RequestLimiter(QPS = 1, timeout = 200, timeunit =TimeUnit.MILLISECONDS, msg ="服务器繁忙,请稍后再试")
    @RequestMapping("getUserList")
    public ResponseDTO getUserList() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(ResponseEnum.Success.getCode());
        responseDTO.setSuccess(ResponseEnum.Success.getStatus());
        responseDTO.setMessage(ResponseEnum.Success.getMessage());
        try {
            List<UserInfo> userList =userInfoService.queryAll();
            responseDTO.setData(userList);
            return responseDTO;
        } catch (Exception e) {
            responseDTO.setCode(ResponseEnum.SERVER_ERROR.getCode());
            responseDTO.setMessage(ResponseEnum.SERVER_ERROR.getMessage());
            return responseDTO;
        }
    }

}
