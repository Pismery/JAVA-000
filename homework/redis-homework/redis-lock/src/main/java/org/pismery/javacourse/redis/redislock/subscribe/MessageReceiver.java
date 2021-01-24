package org.pismery.javacourse.redis.redislock.subscribe;

import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver extends MessageListenerAdapter {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("MessageReceiver:" + message);
    }
}
