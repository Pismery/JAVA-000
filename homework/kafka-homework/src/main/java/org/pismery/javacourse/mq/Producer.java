package org.pismery.javacourse.mq;

import kafka.utils.Json;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer {
    public static void main(String[] args) {
        Properties p = new Properties();
        //127.0.0.1:9092,127.0.0.1:9093,127.0.0.1:9094
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");//kafka地址，多个地址用逗号分割
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(p);

        try {
            for (int i = 0; i < 100; i++) {
                Order order = new Order(2000L + i, System.currentTimeMillis(), "USD2CNY", 6.51d);
                ProducerRecord record = new ProducerRecord(TopicConstant.TEST_TOPIC, order.getId().toString(), Json.encodeAsString(order));
                kafkaProducer.send(record);
                System.out.println("消息发送成功:" + record);
            }
        } finally {
            kafkaProducer.close();
        }
    }

}
