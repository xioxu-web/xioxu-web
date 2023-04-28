package io.microservice.oauth2.cloud.auth.server.support.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import io.microservice.oauth2.cloud.admin.api.entity.SysLog;
import io.microservice.oauth2.cloud.common.log.event.SysLogEvent;
import io.microservice.oauth2.cloud.common.log.util.SysLogUtils;
import io.microservice.oauth2.cloud.common.security.service.OauthUser;
import io.microservice.oauth2.common.core.constant.CommonConstants;
import io.microservice.oauth2.common.core.constant.SecurityConstants;
import io.microservice.oauth2.common.core.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * @author xiaoxu123
 */
@Slf4j
public class Oauth2AuthenticationSuccessEventHandler implements AuthenticationSuccessHandler {

    private final HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter =new OAuth2AccessTokenResponseHttpMessageConverter();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;
        Map<String, Object> map = accessTokenAuthentication.getAdditionalParameters();
        if(MapUtil.isNotEmpty(map)){
            // 发送异步日志事件
            OauthUser userInfo  = (OauthUser) map.get( SecurityConstants.DETAILS_USER );
            log.info("用户: {} 登录成功:",userInfo.getName());
            SecurityContextHolder.getContext().setAuthentication(accessTokenAuthentication);
            SysLog logVo = SysLogUtils.getSysLog();
            logVo.setTitle("登录成功");
            String startTimeStr = request.getHeader( CommonConstants.REQUEST_START_TIME );
            if(StrUtil.isNotBlank(startTimeStr)){
                long startTime = Long.parseLong( startTimeStr );
                long endTime  = System.currentTimeMillis();
                logVo.setTime(endTime-startTime);
            }
           logVo.setCreateBy(userInfo.getUsername());
           logVo.setUpdateBy(userInfo.getUsername());
            SpringContextHolder.publishEvent(new SysLogEvent(logVo));
        }
        //输出token
        sendAccessTokenResponse(request,response,authentication);
    }

    private void sendAccessTokenResponse(HttpServletRequest request,HttpServletResponse response,Authentication authentication) throws IOException {
        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication= (OAuth2AccessTokenAuthenticationToken) authentication;
        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
        Map<String, Object> additionalParameters  = accessTokenAuthentication.getAdditionalParameters();

        OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken( accessToken.getTokenValue() )
                .tokenType( accessToken.getTokenType() )
                .scopes( accessToken.getScopes());
        if(accessToken.getIssuedAt()!=null && accessToken.getExpiresAt()!=null){
          builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(),accessToken.getExpiresAt()));

        if(refreshToken!=null){
           builder.refreshToken(refreshToken.getTokenValue());
        }

        if(!CollectionUtil.isEmpty(additionalParameters)){
           builder.additionalParameters(additionalParameters);
        }

        OAuth2AccessTokenResponse accessTokenResponse = builder.build();

        ServletServerHttpResponse serverHttpResponse = new ServletServerHttpResponse( response );

        // 无状态 注意删除 context 上下文的信息
         SecurityContextHolder.clearContext();

         this.accessTokenHttpResponseConverter.write(accessTokenResponse,null,serverHttpResponse);

        }
    }
}
