package io.microservice.oauth2.cloud.common.security.component;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @author xiaoxu123
 * 判断业务接口访问权限工具
 */
public class PermissionService {

    /**
     *判断接口是否有任意访问权限
     * @param
     */
  public boolean hasPermission(String... permissions){
    if(ArrayUtil.isEmpty(permissions)){
       return false;
    }
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication==null){
       return false;
    }
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    return authorities.stream().map(GrantedAuthority::getAuthority).filter(StringUtils::hasText)
            .anyMatch(x-> PatternMatchUtils.simpleMatch(permissions,x));



  }

}
