package io.microservice.oauth2.cloud.common.security.component;

import cn.hutool.core.util.StrUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaoxu123
 * 请求令牌的抽取逻辑
 */
public class OauthBearerTokenExtractor implements BearerTokenResolver {

    private static final Pattern authorizationPattern=Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-:._~+/]+=*)$",
            Pattern.CASE_INSENSITIVE);

    private String bearerTokenHeaderName = HttpHeaders.AUTHORIZATION;

    private boolean allowFormEncodedBodyParameter=true;

    private boolean allowUriQueryParameter=false;


    private  AntPathMatcher pathMatcher=new AntPathMatcher();

    private final PermitAllUrlProperties urlProperties;

    public OauthBearerTokenExtractor(PermitAllUrlProperties urlProperties){
      this.urlProperties=urlProperties;
    }


    @Override
    public String resolve(HttpServletRequest request) {
        boolean match = urlProperties.getUrls().stream()
                .anyMatch( url -> (pathMatcher.match( url, request.getRequestURI())));
        if(match){
          return null;
        }
       final String authorizationHeaderToken = resolveFromAuthorizationHeader(request);
        final String parameterToken=isParameterTokenSupportedForRequest(request)? resolveFromRequestParameters(request):null;
        if(authorizationHeaderToken!=null){
          if(parameterToken!=null){
              final BearerTokenError error = BearerTokenErrors
                      .invalidRequest("Found multiple bearer tokens in the request");
              throw new OAuth2AuthenticationException(error);
          }
            return authorizationHeaderToken;
        }
        if (parameterToken != null && isParameterTokenEnabledForRequest(request)) {
            return parameterToken;
        }
        return null;
    }

    private String  resolveFromAuthorizationHeader(HttpServletRequest request){
        String authorization  = request.getHeader( this.bearerTokenHeaderName );
        if(!StrUtil.startWithIgnoreCase(authorization,"bearer")){
            return null;
        }
        Matcher matcher = authorizationPattern.matcher( authorization );
        if(!matcher.matches()){
            BearerTokenError error = BearerTokenErrors.invalidToken( "Bearer token is malformed" );
            throw new OAuth2AuthenticationException(error);
        }
        return matcher.group("token");
    }

    private static String resolveFromRequestParameters(HttpServletRequest request){
        String[] access_tokens = request.getParameterValues("access_token");
        if(access_tokens==null || access_tokens.length==0){
            return null;
        }
        if(access_tokens==null || access_tokens.length==1){
            return access_tokens[0];
        }
        BearerTokenError error = BearerTokenErrors.invalidRequest("Found multiple bearer tokens in the request");
        throw new OAuth2AuthenticationException(error);
    }

    private boolean isParameterTokenSupportedForRequest(final HttpServletRequest request){
      return ("POST".equals(request.getMethod()) && MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType())
              || "GET".equals(request.getMethod()));
    }

    private boolean isParameterTokenEnabledForRequest(final HttpServletRequest request) {
        return ((this.allowFormEncodedBodyParameter && "POST".equals(request.getMethod())
                && MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType()))
                || (this.allowUriQueryParameter && "GET".equals(request.getMethod())));
    }



}
