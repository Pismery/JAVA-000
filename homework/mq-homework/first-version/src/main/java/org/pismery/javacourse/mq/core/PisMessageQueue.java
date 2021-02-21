package org.pismery.javacourse.mq.core;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PisMessageQueue {

    private String topic;
    private int capacity;
    private LinkedBlockingQueue<PisMessage> queue;

    public PisMessageQueue(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new LinkedBlockingQueue(capacity);
    }

    public boolean send(PisMessage message) {
        return queue.offer(message);
    }


    public PisMessage receive() {
        return queue.poll();
    }

    public PisMessage receive(Long timeout) {
        try {
            return queue.poll(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
