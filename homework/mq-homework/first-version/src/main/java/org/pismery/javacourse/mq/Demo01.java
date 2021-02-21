package org.pismery.javacourse.mq;


import org.pismery.javacourse.mq.core.PisBroker;
import org.pismery.javacourse.mq.core.PisConsumer;
import org.pismery.javacourse.mq.core.PisMessage;
import org.pismery.javacourse.mq.core.PisProducer;
import org.pismery.javacourse.mq.message.Order;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Demo01 {

    public static void main(String[] args) throws InterruptedException {
        String topic = "test";
        PisBroker broker = new PisBroker();
        broker.createTopic(topic);

        PisConsumer consumer = broker.createConsumer();
        consumer.subscribe(topic);
        final Boolean exit = false;
        Executors.newSingleThreadExecutor().execute(() -> {
            while(!exit) {
                PisMessage<Order> message = consumer.receive();
                if (message == null) continue;
                System.out.println(String.format("body: %s", message.getBody()));
            }
        });

        PisProducer producer = broker.createProducer();
        for (int i = 0; i < 100; i++) {
            Order order = new Order(2000L + i, System.currentTimeMillis(), "USD2CNY", 6.51d);
            PisMessage<Order> message = new PisMessage<>(new HashMap<>(), order);
            producer.send(topic, message);
        }
        TimeUnit.SECONDS.sleep(1L);
    }
}
