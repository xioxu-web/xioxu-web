package io.microservice.oauth2.cloud.common.feign.sentinel.handler;

import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import io.microservice.oauth2.common.core.util.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaoxu123
 * sentinel统一降级限流策略
 */
@Slf4j
public class OauthUrlBlockHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        log.error("sentinel降级 资源名称:{}",e.getRule().getResource(),e);

        response.setContentType( ContentType.JSON.toString());
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.getWriter().println( JSONUtil.toJsonStr( ResultMsg.failed(e.getMessage())));

    }
}
