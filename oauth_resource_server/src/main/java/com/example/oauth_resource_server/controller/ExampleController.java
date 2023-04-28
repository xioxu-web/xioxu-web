package com.example.oauth_resource_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaoxu123
 */
@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @GetMapping("/test")
    public String test() {
        return "Oauth2 服务器授权";
    }

}
