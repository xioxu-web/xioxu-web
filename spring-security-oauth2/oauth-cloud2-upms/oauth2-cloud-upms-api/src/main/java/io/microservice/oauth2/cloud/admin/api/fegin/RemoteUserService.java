package io.microservice.oauth2.cloud.admin.api.fegin;

import io.microservice.oauth2.cloud.admin.api.dto.UserInfo;
import io.microservice.oauth2.common.core.constant.SecurityConstants;
import io.microservice.oauth2.common.core.constant.ServiceNameConstants;
import io.microservice.oauth2.common.core.util.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId ="remoteUserService",value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteUserService {

    /**
     * 通过用户名查询用户、角色信息
     * @param username 用户名
     * @param from 调用标志
     * @return R
     */
    @GetMapping("/user/info/{username}")
    ResultMsg<UserInfo> info(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM) String from);
}
