package cn.iocoder.oauth.module.system.service.mq.message.sms;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 短信渠道的数据刷新 Message
 *
 * @author admin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SmsChannelRefreshMessage extends AbstractChannelMessage {

    @Override
    public String getChannel() {
        return "system.sms-channel.refresh";
    }

}
