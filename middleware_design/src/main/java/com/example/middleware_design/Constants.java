package com.example.middleware_design;

import com.google.common.util.concurrent.RateLimiter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Constants {
    public static Map<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<String, RateLimiter>();
}
