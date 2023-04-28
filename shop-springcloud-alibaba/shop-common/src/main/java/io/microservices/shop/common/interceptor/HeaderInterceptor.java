package io.microservices.shop.common.interceptor;

import io.microservices.shop.common.constant.SecurityConstants;
import io.microservices.shop.common.context.SecurityContextHolder;
import io.microservices.shop.common.utils.ServletUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *自定义请求头拦截器，将Header数据封装到线程变量中方便获取
 * @author xiaoxu123
 */
public class HeaderInterceptor  implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (!(handler instanceof HandlerMethod))
        {
            return true;
        }
        SecurityContextHolder.setUserId( ServletUtils.getHeader(request, SecurityConstants.DETAILS_USER_ID));
        SecurityContextHolder.setUserName(ServletUtils.getHeader(request, SecurityConstants.DETAILS_USERNAME));
        SecurityContextHolder.setUserKey(ServletUtils.getHeader(request, SecurityConstants.USER_KEY));
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception
    {
        SecurityContextHolder.remove();
    }

}
