package cn.iocoder.oauth.module.system.service.mq.message.sms;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 短信模板的数据刷新 Message
 *
 * @author admin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SmsTemplateRefreshMessage extends AbstractChannelMessage {

    @Override
    public String getChannel() {
        return "system.sms-template.refresh";
    }

}
