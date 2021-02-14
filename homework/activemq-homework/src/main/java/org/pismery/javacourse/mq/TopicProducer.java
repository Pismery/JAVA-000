package org.pismery.javacourse.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

public class TopicProducer {

    public static void main(String[] args) throws JMSException, InterruptedException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER
                ,ActiveMQConnectionFactory.DEFAULT_PASSWORD
                ,ActiveMQConnectionFactory.DEFAULT_BROKER_URL
        );

        Connection conn = connectionFactory.createConnection("admin", "admin");
        conn.start();
        Session session = conn.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(new ActiveMQTopic(MQConstant.TEST_TOPIC));

        for (int i = 0; i < 100; i++) {
            Order order = new Order(2000L + i, System.currentTimeMillis(), "USD2CNY", 6.51d);
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("pis test");
            System.out.println("消息发送成功:" + textMessage);
            producer.send(textMessage);
        }

        TimeUnit.SECONDS.sleep(1L);
        session.close();
        conn.close();
    }

}
