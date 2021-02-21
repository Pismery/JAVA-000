package org.pismery.javacourse.mq.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PisBroker {

    private static final int CAPACITY = 1000;
    private final Map<String, PisMessageQueue> mqMap = new ConcurrentHashMap<>(64);

    public void createTopic(String topic) {
        mqMap.putIfAbsent(topic, new PisMessageQueue(topic, CAPACITY));
    }

    public PisMessageQueue findMessageQueue(String topic) {
        return mqMap.get(topic);
    }

    public PisConsumer createConsumer() {
        return new PisConsumer(this);
    }

    public PisProducer createProducer() {
        return new PisProducer(this);
    }

}
