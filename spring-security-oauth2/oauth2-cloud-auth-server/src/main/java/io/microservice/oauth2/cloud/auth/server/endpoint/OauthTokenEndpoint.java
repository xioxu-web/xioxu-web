package io.microservice.oauth2.cloud.auth.server.endpoint;

import cn.hutool.core.util.StrUtil;
import io.microservice.oauth2.cloud.admin.api.entity.SysOauthClientDetails;
import io.microservice.oauth2.cloud.admin.api.fegin.RemoteClientDetailsService;
import io.microservice.oauth2.cloud.common.security.annotation.Inner;
import io.microservice.oauth2.cloud.common.security.util.OAuth2EndpointUtils;
import io.microservice.oauth2.cloud.common.security.util.OAuth2ErrorCodesExpand;
import io.microservice.oauth2.common.core.constant.CacheConstants;
import io.microservice.oauth2.common.core.constant.SecurityConstants;
import io.microservice.oauth2.common.core.util.ResultMsg;
import io.microservice.oauth2.common.core.util.SpringContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.core.http.converter.OAuth2ErrorHttpMessageConverter;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;
import java.util.Set;

/**
 * @author xiaoxu123
 * 删除token端点
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class OauthTokenEndpoint {

    private final HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter = new OAuth2AccessTokenResponseHttpMessageConverter();

    private final HttpMessageConverter<OAuth2Error> errorHttpResponseConverter = new OAuth2ErrorHttpMessageConverter();

    private final OAuth2AuthorizationService authorizationService;

    private final RemoteClientDetailsService clientDetailsService;

    private final RedisTemplate<String, Object> redisTemplate;

    private final CacheManager cacheManager;

    /**
     * 认证页面
     * @param modelAndView
     * @param error 表单登录失败处理回调的错误信息
     * @return ModelAndView
     */
    @GetMapping("/login")
    public ModelAndView require(ModelAndView modelAndView, @RequestParam(required = false) String error) {
        modelAndView.setViewName( "ftl/login" );
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @GetMapping("/confirm_access")
    public ModelAndView confirm(Principal principal, ModelAndView modelAndView,
                                @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                                @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
                                @RequestParam(OAuth2ParameterNames.STATE) String state) {

        ResultMsg<SysOauthClientDetails> r = clientDetailsService.getClientDetailsById(clientId, SecurityConstants.FROM_IN);
        SysOauthClientDetails clientDetails = r.getData();
        Set<String> authorizedScopes = StringUtils.commaDelimitedListToSet(clientDetails.getScope());
        modelAndView.addObject("clientId", clientId);
        modelAndView.addObject("state", state);
        modelAndView.addObject("scopeList", authorizedScopes);
        modelAndView.addObject("principalName", principal.getName());
        modelAndView.setViewName( "ftl/confirm" );
        return modelAndView;
    }

    /**
     *校验token令牌
     *
     */
    @SneakyThrows
    @GetMapping("/checkToken")
    public void checkToken(String token, HttpServletResponse response){
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse( response );
        if(StrUtil.isBlank(token)){
          httpResponse.setStatusCode( HttpStatus.UNAUTHORIZED);
          this.errorHttpResponseConverter.write(new OAuth2Error( OAuth2ErrorCodesExpand.TOKEN_MISSING ),null
          ,httpResponse);
        }

        OAuth2Authorization authorization = authorizationService.findByToken( token, OAuth2TokenType.ACCESS_TOKEN);

        // 如果令牌不存在 返回401
        if(authorization==null){
           httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
           this.errorHttpResponseConverter.write(new OAuth2Error( OAuth2ErrorCodesExpand.TOKEN_MISSING),null
           ,httpResponse);
        }

        Map<String, Object> claims = authorization.getAccessToken().getClaims();
        OAuth2AccessTokenResponse sendAccessTokenResponse = OAuth2EndpointUtils.sendAccessTokenResponse( authorization, claims );

        this.accessTokenHttpResponseConverter.write(sendAccessTokenResponse, MediaType.APPLICATION_JSON, httpResponse);

    }

    /**
     * 令牌管理调用
     * @param token token
     */
    @Inner
    @DeleteMapping("/{token}")
    public ResultMsg<Boolean> removeToken(@PathVariable("token") String token) {
        OAuth2Authorization authorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        if (accessToken == null || StrUtil.isBlank(accessToken.getToken().getTokenValue())) {
            return ResultMsg.ok();
        }
        // 清空用户信息
        cacheManager.getCache( CacheConstants.USER_DETAILS).evict(authorization.getPrincipalName());
        // 清空access token
        authorizationService.remove(authorization);
        // 处理自定义退出事件，保存相关日志
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringContextHolder.publishEvent(new LogoutSuccessEvent(authentication));
        return ResultMsg.ok();
    }


}
