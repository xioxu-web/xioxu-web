package com.example.resourceserveronsso.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaoxu123
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/info")
    public Authentication info(Authentication authentication) {
        return authentication;
    }

}
