package cn.iocoder.oauth.module.system.service.mq.producer;
import cn.iocoder.oauth.framework.common.core.KeyValue;
import cn.iocoder.oauth.module.system.service.mq.message.sms.SmsChannelRefreshMessage;
import cn.iocoder.oauth.module.system.service.mq.message.sms.SmsSendMessage;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiaoxu123
 */
@Component
public class SmsProducer {
    private Logger logger= LoggerFactory.getLogger( SmsProducer.class);

    @Resource
    private KafkaTemplate<String,Object> kafkaTemplate;

    /**
     * MQ主题:短信通知
     *
     * 启动zk：bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
     * 启动kafka：bin/kafka-server-start.sh -daemon config/server.properties
     * 创建topic：bin/kafka-topics.sh --create --zookeeper 172.17.0.5:2181 --replication-factor 1 --partitions 1 --topic lottery_invoice
     */
    public static final String TOPIC_INVOICE = "sendmsg_invoice";

    /**
     * 发送 {@link SmsChannelRefreshMessage} 消息
     */
    public void sendSmsChannelRefreshMessage() {
        SmsChannelRefreshMessage message = new SmsChannelRefreshMessage();
        Object messageJson = JSON.toJSON( message );
        kafkaTemplate.send( (ProducerRecord<String, Object>) messageJson );
    }


    /**
     * 发送 {@link SmsSendMessage} 消息
     *
     * @param logId 短信日志编号
     * @param mobile 手机号
     * @param channelId 渠道编号
     * @param apiTemplateId 短信模板编号
     * @param templateParams 短信模板参数
     */
    public void sendSmsSendMessage(Long logId, String mobile,
                                   Long channelId, String apiTemplateId, List<KeyValue<String, Object>> templateParams) {
        SmsSendMessage message = new SmsSendMessage().setLogId(logId).setMobile(mobile);
        message.setChannelId(channelId).setApiTemplateId(apiTemplateId).setTemplateParams(templateParams);
        String objJson = JSON.toJSONString(message);
        logger.info("发送MQ消息(用户手机号短信发送) topic：{} logId：{} message：{}", TOPIC_INVOICE, message.getLogId(), objJson);
        kafkaTemplate.send(TOPIC_INVOICE, objJson);
    }

}
