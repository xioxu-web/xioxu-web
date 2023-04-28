package io.microservice.oauth2.cloud.common.security.util;

import cn.hutool.core.util.StrUtil;
import io.microservice.oauth2.cloud.common.security.service.OauthUser;
import io.microservice.oauth2.common.core.constant.SecurityConstants;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author xiaoxu123
 * 安全工具类
 */
@UtilityClass
public class SecurityUtils {

    /**
     * 获取Authentication
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户信息
     */
    public OauthUser getUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof OauthUser) {
            return (OauthUser) principal;
        }
        return null;
    }

    /**
     * 获取用户
     */
    public
    OauthUser getUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return getUser( authentication );
    }

    /**
     * 获取用户角色信息
     */
    public List<Long> getRoles() {
        Authentication authentication = getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<Long> roleIds = new ArrayList<>();
        authorities.stream().filter(granted -> StrUtil.startWith(granted.getAuthority(), SecurityConstants.ROLE ) )
                .forEach( granted -> {
                    String id = StrUtil.removePrefix( granted.getAuthority(), SecurityConstants.ROLE );
                    roleIds.add( Long.getLong( id ) );
                });

        return roleIds;
    }

}

