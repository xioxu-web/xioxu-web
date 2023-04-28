package cn.iocoder.oauth.module.system.service.mq.consumer.sms;
import cn.iocoder.oauth.module.system.service.mq.message.sms.SmsSendMessage;
import cn.iocoder.oauth.module.system.service.sms.SmsSendService;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Optional;

/**
 * 针对 {@link SmsSendMessage} 的消费者
 *
 * @author zzf
 */
@Component
public class SmsSendConsumerListener{

    private Logger logger = LoggerFactory.getLogger(SmsSendConsumerListener.class);

    @Resource
    private SmsSendService smsSendService;

    @KafkaListener(topics = "sendmsg_invoice", groupId = "message")
    public void onMessage(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> message = Optional.ofNullable(record.value());
        // 1. 判断消息是否存在
        if (!message.isPresent()) {
            return;
        }
        // 2. 处理 MQ 消息
        try {
            // 1. 转化对象
            SmsSendMessage smsSendMessage = JSON.parseObject((String) message.get(), SmsSendMessage.class);
            // 2 执行用户短信消息发送
            smsSendService.doSendSms(smsSendMessage);
            // 3. 打印日志
            logger.info("消费MQ消息，完成 topic：{} bizId：{} 通知结果：{}", topic, smsSendMessage.getLogId(), JSON.toJSONString(smsSendMessage));
            // 4. 消息消费完成
            ack.acknowledge();
        } catch (Exception e) {
            // 发奖环节失败，消息重试。所有到环节，发货、更新库，都需要保证幂等。
            logger.error("消费MQ消息，失败 topic：{} message：{}", topic, message.get());
            throw e;
        }
    }


}
