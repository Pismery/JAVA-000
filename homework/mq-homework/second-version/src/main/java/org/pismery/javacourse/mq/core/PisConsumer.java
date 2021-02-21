package org.pismery.javacourse.mq.core;

public class PisConsumer {

    private PisBroker broker;
    private int offset;
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
        this.offset = 0;
    }

    public PisMessage receive() {
        if (messageQueue == null) {
            return null;
        }

        PisMessage result = messageQueue.receive(offset);
        if (null != result) {
            offset++;
        }

        return result;
    }

}
