package com.example.middleware_design.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiaoxu123
 */

@ConfigurationProperties("service.whitelist")
public class StarterServiceProperties {
    private String userStr;

    public String getUserStr() {
        return userStr;
    }

    public void setUserStr(String userStr) {
        this.userStr = userStr;
    }
}
