package io.microservice.oauth2.cloud.common.log.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author xiaoxu123
 * 系统日志事件
 */
public class SysLogEvent extends ApplicationEvent {

    public SysLogEvent(Object source) {
        super(source);
    }
}
