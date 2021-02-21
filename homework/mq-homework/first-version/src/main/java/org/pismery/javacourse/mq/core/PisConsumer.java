package org.pismery.javacourse.mq.core;

public class PisConsumer {

    private PisBroker broker;
    private PisMessageQueue messageQueue;

    public PisConsumer(PisBroker broker) {
        this.broker = broker;
    }

    public void subscribe(String topic) {
        PisMessageQueue messageQueue = broker.findMessageQueue(topic);
        if (null == messageQueue) {
            throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
        }
        this.messageQueue = messageQueue;
    }

    public PisMessage receive() {
        return messageQueue.receive();
    }

}
