package org.pismery.javacourse.mq.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PisMessageQueue {

    private String topic;
    private int capacity;
    private int size;
    private List<PisMessage> queue;

    public PisMessageQueue(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new ArrayList<>(64);
    }

    public boolean send(PisMessage message) {
        if (size >= capacity) {
            throw new IndexOutOfBoundsException();
        }

        size++;
        return queue.add(message);
    }


    public PisMessage receive(int offset) {
        if (offset >= size) {
            return null;
        }

        return queue.get(offset);
    }
}
