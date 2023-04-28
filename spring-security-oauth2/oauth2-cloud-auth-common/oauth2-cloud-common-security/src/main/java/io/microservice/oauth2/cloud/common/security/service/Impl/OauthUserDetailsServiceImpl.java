package io.microservice.oauth2.cloud.common.security.service.Impl;
import io.microservice.oauth2.cloud.admin.api.dto.UserInfo;
import io.microservice.oauth2.cloud.admin.api.fegin.RemoteUserService;
import io.microservice.oauth2.cloud.common.security.service.OauthUser;
import io.microservice.oauth2.cloud.common.security.service.OauthUserDetailsService;
import io.microservice.oauth2.common.core.constant.CacheConstants;
import io.microservice.oauth2.common.core.constant.SecurityConstants;
import io.microservice.oauth2.common.core.util.ResultMsg;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;


@RequiredArgsConstructor
public class OauthUserDetailsServiceImpl implements OauthUserDetailsService {

    private final RemoteUserService remoteUserService;

    private final CacheManager cacheManager;

    /**
     * 用户名密码登录
     * @param username 用户名
     * @return
     */
    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        Cache cache = cacheManager.getCache( CacheConstants.USER_DETAILS);
        if (cache != null && cache.get(username) != null) {
            return (OauthUser) cache.get(username).get();
        }

        ResultMsg<UserInfo> result = remoteUserService.info(username, SecurityConstants.FROM_IN);
        UserDetails userDetails = getUserDetails(result);
        if (cache != null) {
            cache.put(username, userDetails);
        }
        return userDetails;
    }



    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
