package org.pismery.javacourse.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;

public class TopicConsumer {

    public static void main(String[] args) throws JMSException, InterruptedException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://127.0.0.1:61616"
        );

        Connection conn = connectionFactory.createConnection();
        conn.start();
        Session session = conn.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(new ActiveMQTopic(MQConstant.TEST_TOPIC));

        // consumer.setMessageListener(new MessageListener() {
        //     @Override
        //     public void onMessage(Message message) {
        //         System.out.println("test: "+message);
        //         try {
        //             message.acknowledge();
        //         } catch (JMSException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // });

        while (true) {
            Message message = consumer.receive();
            System.out.println(message);
        }
    }


}
