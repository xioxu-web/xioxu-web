package io.microservice.oauth2.cloud.auth.server.support.base;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

import javax.annotation.Nullable;
import java.util.*;

/**
 * @author xiaoxu123
 * 自定义授权模式抽象
 */
public abstract class OAuth2ResourceOwnerBaseAuthenticationToken extends AbstractAuthenticationToken {

    @Getter
    private AuthorizationGrantType authorizationGrantType;

    @Getter
    private Authentication clientPrincipal;

    @Getter
    private  Set<String> scopes;

    @Getter
    private Map<String, Object> additionalParameters;


    public OAuth2ResourceOwnerBaseAuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                                      Authentication clientPrincipal, @Nullable Set<String> scopes,
                                                      @Nullable Map<String, Object> additionalParameters) {
        super( Collections.emptyList());
        Assert.notNull(authorizationGrantType,"authorizationGrantType cannot be null");
        Assert.notNull(clientPrincipal,"clientPrincipal cannot be null");
        this.authorizationGrantType=authorizationGrantType;
        this.clientPrincipal=clientPrincipal;
        this.scopes=Collections.unmodifiableSet(scopes!=null? new HashSet<>(scopes): Collections.emptySet());
        this.additionalParameters=Collections.unmodifiableMap(additionalParameters!=null? new HashMap<>(additionalParameters): Collections.emptyMap());
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return this.clientPrincipal;
    }
}
