package org.pismery.javacourse.redis.redislock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
@RequestMapping("pubSub")
public class PubSubController {

    private static final ExecutorService subscriberExecutor = Executors.newFixedThreadPool(3);

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/jedisDemo")
    public Long jedisDemo() throws InterruptedException {
        IntStream.range(0, 3).forEach(i -> {
            subscriberExecutor.execute(() -> {
                Jedis resource = jedisPool.getResource();
                JedisPubSub jedisPubSub = new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        if (message.isEmpty()) {
                            System.out.println("SubPub End");
                            this.unsubscribe(channel);
                            resource.close();
                        }

                        System.out.println(channel + ": " + message);
                    }
                };
                resource.subscribe(jedisPubSub, "pis-channel");
            });
        });


        Long reply;
        try (Jedis resource = jedisPool.getResource()){
            TimeUnit.SECONDS.sleep(1L);
            reply = resource.publish("pis-channel", "pis test message");
            resource.publish("pis-channel", "");
        }

        return reply;
    }

    @GetMapping("/redisTemplateDemo")
    public void redisTemplateDemo() {
        redisTemplate.convertAndSend("pis-topic", "pis test message");
    }
}
