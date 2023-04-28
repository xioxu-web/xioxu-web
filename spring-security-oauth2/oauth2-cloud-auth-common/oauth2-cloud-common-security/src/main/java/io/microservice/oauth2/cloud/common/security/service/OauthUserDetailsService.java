package io.microservice.oauth2.cloud.common.security.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import io.microservice.oauth2.cloud.admin.api.dto.UserDTO;
import io.microservice.oauth2.cloud.admin.api.dto.UserInfo;
import io.microservice.oauth2.common.core.constant.CommonConstants;
import io.microservice.oauth2.common.core.constant.SecurityConstants;
import io.microservice.oauth2.common.core.util.ResultMsg;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author xiaoxu123
 * 加载用户相关信息
 */
public interface OauthUserDetailsService extends UserDetailsService,Ordered {

    /**
     *是否支持客户端
     * @param clientId 目标客户端
     * @return true/false
     */
   default boolean support(String clientId, String grantType){
       return true;
   }

    /**
     *构建用户信息
     * @param resultMsg
     * @return UserDetails
     */
   default UserDetails getUserDetails(ResultMsg<UserInfo> resultMsg) throws UsernameNotFoundException {
     if(resultMsg==null || resultMsg.getData()==null){
       return (UserDetails) new UsernameNotFoundException("该用户不存在");
     }
     UserInfo info = resultMsg.getData();
     HashSet<Object> dbAuthsSet = new HashSet<>();
       if(ArrayUtil.isNotEmpty(info.getRoles())){
         //获取角色
           Arrays.stream(info.getRoles()).forEach(role->dbAuthsSet.add(SecurityConstants.ROLE+role));
        //获取资源
         dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));
     }
       Collection<GrantedAuthority> authorities= AuthorityUtils.createAuthorityList( dbAuthsSet.toArray( new String[0] ) );
       UserDTO sysUser = info.getSysUser();

       // 构造security用户
       return new OauthUser(sysUser.getUserId(),sysUser.getDeptId(),sysUser.getUsername(),
               SecurityConstants.BCRYPT + sysUser.getPassword(),sysUser.getPhone(),true, true, true,
               StrUtil.equals(sysUser.getDelFlag(), CommonConstants.STATUS_NORMAL),authorities);

   }

    /**
     * 通过用户实体查询
     * @param oauthUser
     * @return
     */
    default UserDetails loadUserByUser(OauthUser oauthUser) {
        return this.loadUserByUsername(oauthUser.getUsername());
    }

}
