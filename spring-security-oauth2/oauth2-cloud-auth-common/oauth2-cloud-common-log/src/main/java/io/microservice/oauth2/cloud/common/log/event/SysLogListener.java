package io.microservice.oauth2.cloud.common.log.event;
import io.microservice.oauth2.cloud.admin.api.entity.SysLog;
import io.microservice.oauth2.cloud.admin.api.fegin.RemoteLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @author xiaoxu123
 * 系统日志监听器
 */
@RequiredArgsConstructor
@Slf4j
public class SysLogListener {

    private final RemoteLogService remoteLogService;


    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void  saveSysLog(SysLogEvent event){
        SysLog sysLog = (SysLog) event.getSource();
        remoteLogService.saveLog(sysLog);
    }
}
