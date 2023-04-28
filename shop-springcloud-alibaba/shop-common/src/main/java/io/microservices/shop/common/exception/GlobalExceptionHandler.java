package io.microservices.shop.common.exception;

import io.microservices.shop.common.constant.HttpCode;
import io.microservices.shop.common.resp.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collection;

/**
 * 统一异常处理
 * @author xiaoxu123
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception e){
        logger.error("服务器抛出了异常：{}", e);
        return new Result(false,HttpCode.FAILURE,"执行失败", e.getMessage());
    }

}
