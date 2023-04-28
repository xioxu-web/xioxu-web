package com.example.middleware_design.Interceptor;

import com.example.middleware_design.annotation.RequestLimiter;
import com.example.middleware_design.enums.ResponseEnum;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaoxu123
 * 请求限流器
 */
@Component
@Slf4j
public class RequestLimiterInterceptor extends AbstractInterceptor{

    /**
     * 不同的方法存到不同的RateLimiter容器
     */
    private final Map<String, RateLimiter> rateLimiterMap=new ConcurrentHashMap<String,RateLimiter>();

    @Override
    protected ResponseEnum preFilter(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                RequestLimiter rateLimit = handlerMethod.getMethodAnnotation(RequestLimiter.class);
                //判断是否有注解
                if (rateLimit != null) {
                    //获取请求的url
                    String key = request.getMethod() + request.getRequestURI();
                    RateLimiter rateLimiter;
                    //判断Map集合是否有创建好的令牌桶
                    if (!rateLimiterMap.containsKey(key)) {
                        //创建令牌桶，以r/s放入令牌
                        rateLimiter = RateLimiter.create(rateLimit.QPS());
                        rateLimiterMap.put(key, rateLimiter);
                    }
                    //获取令牌
                    rateLimiter = rateLimiterMap.get(key);
                    boolean tryAcquire = rateLimiter.tryAcquire(rateLimit.timeout(), rateLimit.timeunit());
                    if (tryAcquire) {
                        //获取令牌成功
                        return ResponseEnum.Success;
                    } else {
                        log.error("请求被限流......,url:{}", request.getServletPath());
                        return ResponseEnum.RATE_LIMIT;
                    }

                }
            }
            return ResponseEnum.Success;
        }catch (Exception e){
          e.printStackTrace();
          return ResponseEnum.FAIL;
        }

    }
}
