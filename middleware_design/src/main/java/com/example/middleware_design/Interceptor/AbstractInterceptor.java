package com.example.middleware_design.Interceptor;
import com.alibaba.fastjson.JSON;
import com.example.middleware_design.dto.ResponseDTO;
import com.example.middleware_design.enums.ResponseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 抽象拦截器
 * @author xiaoxu123
 */
public abstract class AbstractInterceptor extends HandlerInterceptorAdapter {
    private Logger logger= LoggerFactory.getLogger(AbstractInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ResponseEnum result;
        try {
            result= preFilter(request, response, handler);
        }catch (Exception e){
            logger.error("preHandle catch a exception:"+e.getMessage());
            result=ResponseEnum.FAIL;
        }
        if(ResponseEnum.Success.code.equals(result.code)){
           return true;
        }
        HandlerResponse(result,response);
        return false;
    }

    /**
     * 自定义拦截器
     * @param request
     * @return
     */
    protected abstract ResponseEnum preFilter(HttpServletRequest request,HttpServletResponse response,Object handler);


    /**
     * 处理错误信息
     * @param response
     * @param result
     * @return
     */
    private void HandlerResponse(ResponseEnum result,HttpServletResponse response) throws IOException {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(result.getCode());
        responseDTO.setSuccess(result.getStatus());
        responseDTO.setMessage(result.getMessage());
        responseDTO.setData(null);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        ServletOutputStream outputStream=null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write((JSON.toJSONString(responseDTO)).getBytes());
        } catch (IOException e) {
            logger.error("handlerResponse catch a exception:" + e.getMessage());
        }finally {
            outputStream.flush();
        }
    }
}
