package io.microservice.oauth2.cloud.common.security.service;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoxu123
 */
public class OauthUser extends User implements OAuth2AuthenticatedPrincipal {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 用户ID
     */
    @Getter
    @JsonSerialize(using = ToStringSerializer.class)
    private final Long id;

    /**
     * 部门ID
     */
    @Getter
    @JsonSerialize(using = ToStringSerializer.class)
    private final Long deptId;

    /**
     * 手机号
     */
    @Getter
    private final String phone;

    public OauthUser(Long id, Long deptId, String username, String password, String phone, boolean enabled,
              boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
              Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.deptId = deptId;
        this.phone = phone;
    }

    /**
     * Get the OAuth 2.0 token attributes
     * @return the OAuth 2.0 token attributes
     */
    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<>();
    }

    @Override
    public String getName() {
        return this.getUsername();
    }

}
