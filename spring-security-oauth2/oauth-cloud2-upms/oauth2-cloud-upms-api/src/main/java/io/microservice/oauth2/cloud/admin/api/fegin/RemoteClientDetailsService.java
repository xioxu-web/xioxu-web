package io.microservice.oauth2.cloud.admin.api.fegin;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.microservice.oauth2.cloud.admin.api.entity.SysOauthClientDetails;
import io.microservice.oauth2.common.core.constant.SecurityConstants;
import io.microservice.oauth2.common.core.constant.ServiceNameConstants;
import io.microservice.oauth2.common.core.util.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author xiaoxu123
 */
@FeignClient(contextId = "remoteClientDetailsService",value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteClientDetailsService {

    /**
     * 通过clientId 查询客户端信息
     * @param clientId 用户名
     * @param from 调用标志
     * @return R
     */
    @GetMapping("/client/getClientDetailsById/{clientId}")
    ResultMsg<SysOauthClientDetails> getClientDetailsById(@PathVariable("clientId") String clientId,
                                                  @RequestHeader(SecurityConstants.FROM) String from);

    /**
     * 查询全部客户端
     * @param from 调用标识
     * @return R
     */
    @GetMapping("/client/list")
    ResultMsg<List<SysOauthClientDetails>> listClientDetails(@RequestHeader(SecurityConstants.FROM) String from);

}
