package cn.iocoder.oauth.framework.sms.core.client;

import cn.iocoder.oauth.framework.sms.core.property.SmsChannelProperties;

/**
 * 短信的客户端工厂接口
 * @author xiaoxu123
 */
public interface SmsClientFactory {
    /**
     * 获得短信 Client
     *
     * @param channelId 渠道编号
     * @return 短信 Client
     */
    SmsClient getSmsClient(Long channelId);

    /**
     * 获得短信 Client
     *
     * @param channelCode 渠道编码
     * @return 短信 Client
     */
    SmsClient getSmsClient(String channelCode);

    /**
     * 创建短信 Client
     *
     * @param properties 配置对象
     */
    void createOrUpdateSmsClient(SmsChannelProperties properties);

}
