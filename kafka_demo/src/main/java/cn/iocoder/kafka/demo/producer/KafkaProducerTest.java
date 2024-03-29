package cn.iocoder.kafka.demo.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import javax.annotation.Resource;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * kafka生产者
 * @author xiaoxu123
 */
public class KafkaProducerTest {

    private static Producer<String, String> createProducer() {
        // 设置 Producer 的属性
        Properties properties = new Properties();
        // 设置 Broker 的地址
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        // 0-不应答。1-leader 应答。all-所有 leader 和 follower 应答。
        properties.put("acks", "1");
        // 发送失败时，重试发送的次数
        properties.put("retries", 3);
        // 消息的 key 的序列化方式
        properties.put("key.serializer", StringSerializer.class.getName());
        // 消息的 value 的序列化方式
        properties.put("value.serializer", StringSerializer.class.getName());

        // 创建 KafkaProducer 对象
        // 因为我们消息的 key 和 value 都使用 String 类型，所以创建的 Producer 是 <String, String> 的泛型。
        return new KafkaProducer<>(properties);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建 KafkaProducer 对象
        Producer<String, String> producer = createProducer();

        // 创建消息。传入的三个参数，分别是 Topic ，消息的 key ，消息的 message 。
        ProducerRecord<String, String> message = new ProducerRecord<>("product-topic1", "key", "消息中间件kafka");

        // 同步发送消息
        Future<RecordMetadata> sendResultFuture = producer.send(message);
        RecordMetadata result = sendResultFuture.get();
        System.out.println("message sent to " + result.topic() + ", partition " + result.partition() + ", offset " + result.offset());
    }


}
