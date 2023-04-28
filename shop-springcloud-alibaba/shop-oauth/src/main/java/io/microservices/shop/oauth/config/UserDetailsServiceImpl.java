package io.microservices.shop.oauth.config;
import com.alibaba.fastjson.JSONObject;
import io.microservices.shop.common.resp.Result;
import io.microservices.shop.common.utils.StringUtils;
import io.microservices.shop.oauth.util.UserJwt;
import io.microservices.shop.service.user.api.fegin.UserFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 自定义授权认证类
 * @author xiaoxu123
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private Logger logger= LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Resource
    private ClientDetailsService clientDetailsService;

    @Resource
    private  UserFeign userFeign;

    /**
     * 自定义授权认证
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if (authentication == null) {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if (clientDetails != null) {
                // 秘钥
                String clientSecret = clientDetails.getClientSecret();
                // 静态方式
               return new User(username, new BCryptPasswordEncoder().encode(clientSecret), AuthorityUtils.commaSeparatedStringToAuthorityList(""));
                //数据库查找方式
                /*return new User(username, // 客户端 ID
                        clientSecret, // 客户端密钥
                        AuthorityUtils.commaSeparatedStringToAuthorityList(""));*/
            }
        }

        /**
         * 用户账号信息验证
         */
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        // 根据用户名查询用户信息
        String pwd =new BCryptPasswordEncoder().encode("szitheima");
        // 创建User对象
        String permissions = "user,admin";

        UserJwt userDetails = new UserJwt(username, pwd, AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));

        return userDetails;
    }

}
