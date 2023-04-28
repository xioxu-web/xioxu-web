package io.microservice.oauth2.cloud.admin.api.fegin;

import io.microservice.oauth2.cloud.admin.api.entity.SysLog;
import io.microservice.oauth2.common.core.constant.SecurityConstants;
import io.microservice.oauth2.common.core.constant.ServiceNameConstants;
import io.microservice.oauth2.common.core.util.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xiaoxu123
 */
@FeignClient(contextId ="remoteLogService",value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteLogService {

    /**
     * 保存日志
     * @param sysLog
     * @return
     */
    @PostMapping(value ="/log",headers = SecurityConstants.HEADER_FROM_IN)
    ResultMsg<Boolean> saveLog(@RequestBody SysLog sysLog);
}
