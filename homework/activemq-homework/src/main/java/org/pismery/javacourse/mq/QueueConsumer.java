package org.pismery.javacourse.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueueConsumer {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://127.0.0.1:61616"
        );

        Connection conn = connectionFactory.createConnection();
        conn.start();
        Session session = conn.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(session.createQueue(MQConstant.TEST_QUEUE));

        while (true) {
            Message message = consumer.receive();
            System.out.println(message);
        }
    }

}
