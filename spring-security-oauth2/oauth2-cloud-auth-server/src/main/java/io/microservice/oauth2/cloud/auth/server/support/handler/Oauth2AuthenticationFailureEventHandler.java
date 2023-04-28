package io.microservice.oauth2.cloud.auth.server.support.handler;

import cn.hutool.core.util.StrUtil;
import io.microservice.oauth2.cloud.admin.api.entity.SysLog;
import io.microservice.oauth2.cloud.common.log.event.SysLogEvent;
import io.microservice.oauth2.cloud.common.log.util.LogTypeEnum;
import io.microservice.oauth2.cloud.common.log.util.SysLogUtils;
import io.microservice.oauth2.common.core.constant.CommonConstants;
import io.microservice.oauth2.common.core.util.ResultMsg;
import io.microservice.oauth2.common.core.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiaoxu123
 */
@Slf4j
public class Oauth2AuthenticationFailureEventHandler implements AuthenticationFailureHandler {

    private final MappingJackson2HttpMessageConverter errorHttpResponseConverter = new MappingJackson2HttpMessageConverter();


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username= request.getParameter( OAuth2ParameterNames.USERNAME );
        log.info("用户：{} 登录失败，异常：{}", username, exception.getLocalizedMessage());
        SysLog logVo  = SysLogUtils.getSysLog();
        logVo.setTitle("登录失败");
        logVo.setType( LogTypeEnum.ERROR.getType() );
        logVo.setException(exception.getLocalizedMessage());
        //发送异步日志
        String startTimeStr = request.getHeader(CommonConstants.REQUEST_START_TIME);
        if(StrUtil.isNotBlank(startTimeStr)){
            long startTime = Long.parseLong( startTimeStr );
            long endTime  = System.currentTimeMillis();
            logVo.setTime(endTime-startTime);
        }
        logVo.setCreateBy(username);
        logVo.setUpdateBy(username);
        SpringContextHolder.publishEvent(new SysLogEvent(logVo));
    }


    private void sendErrorResponse(HttpServletResponse response, AuthenticationException exception) throws IOException {
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        httpResponse.setStatusCode( HttpStatus.UNAUTHORIZED);
        this.errorHttpResponseConverter.write(ResultMsg.failed(exception.getLocalizedMessage()),MediaType.APPLICATION_JSON,httpResponse);
    }
}
