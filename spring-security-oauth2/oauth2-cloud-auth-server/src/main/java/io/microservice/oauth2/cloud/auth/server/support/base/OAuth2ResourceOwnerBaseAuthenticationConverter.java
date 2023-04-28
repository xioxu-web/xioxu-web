package io.microservice.oauth2.cloud.auth.server.support.base;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * @author xiaoxu123
 * 自定义认证扎转换器
 */
public abstract class OAuth2ResourceOwnerBaseAuthenticationConverter <T extends OAuth2ResourceOwnerBaseAuthenticationToken>
        implements AuthenticationConverter {

    /**
     * 是否支持此convert
     * @param grantType 授权类型
     * @return
     */
    public abstract boolean support(String grantType);

    /**
     * 校验参数
     * @param request 请求
     */
    public void checkParams(HttpServletRequest request) {

    }

    /**
     * 构建具体类型的token
     * @param clientPrincipal
     * @param requestedScopes
     * @param additionalParameters
     * @return
     */
    public abstract T buildToken(Authentication clientPrincipal, Set<String> requestedScopes,
                                 Map<String, Object> additionalParameters);


    @Override
    public Authentication convert(HttpServletRequest request) {
        return null;
    }


}
