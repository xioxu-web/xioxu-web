package com.example.middleware_design.config;
import org.springframework.util.StringUtils;

/**
 * @author xiaoxu123
 */
public class StarterService {

    private String userStr;

    public StarterService(String userStr) {
        this.userStr = userStr;
    }

    public String[] split(String separatorChar) {
        return StringUtils.split(this.userStr, separatorChar);
    }
    }

