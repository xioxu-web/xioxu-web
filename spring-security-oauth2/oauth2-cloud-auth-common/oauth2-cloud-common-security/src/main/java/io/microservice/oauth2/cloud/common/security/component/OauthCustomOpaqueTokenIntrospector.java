package io.microservice.oauth2.cloud.common.security.component;

import cn.hutool.extra.spring.SpringUtil;
import io.microservice.oauth2.cloud.common.security.service.OauthUser;
import io.microservice.oauth2.cloud.common.security.service.OauthUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

/**
 * @author xiaoxu123
 */
@RequiredArgsConstructor
public class OauthCustomOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final OAuth2AuthorizationService authorizationService;

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        OAuth2Authorization oldAuthorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);

        Map<String, OauthUserDetailsService> userDetailsServiceMap = SpringUtil
                .getBeansOfType(OauthUserDetailsService.class);

        Optional<OauthUserDetailsService> optional = userDetailsServiceMap.values().stream()
                .filter(service -> service.support(oldAuthorization.getRegisteredClientId(),
                        oldAuthorization.getAuthorizationGrantType().getValue()))
                .max( Comparator.comparingInt( Ordered::getOrder));

        UserDetails userDetails = null;
        try {
            userDetails = optional.get().loadUserByUsername(oldAuthorization.getPrincipalName());
        }
        catch (UsernameNotFoundException notFoundException) {
        }

        OauthUser user = (OauthUser) userDetails;
        return user;
    }


}
