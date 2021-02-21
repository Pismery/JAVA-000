package org.pismery.javacourse.mq.core;

public class PisProducer {
    private PisBroker broker;

    public PisProducer(PisBroker broker) {
        this.broker = broker;
    }

    public void send(String topic, PisMessage message) {
        PisMessageQueue messageQueue = broker.findMessageQueue(topic);
        if (null == messageQueue) {
            throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
        }
        messageQueue.send(message);
    }
}
