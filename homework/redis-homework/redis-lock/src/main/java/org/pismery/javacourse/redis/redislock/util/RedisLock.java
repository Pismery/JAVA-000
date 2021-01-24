package org.pismery.javacourse.redis.redislock.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
public class RedisLock {

    private static final String LOCK_PREFIX = "redis-lock-";

    private final RedisTemplate<String, String> redisTemplate;

    private final ObjectMapper mapper;

    public RedisLock(RedisTemplate<String, String> redisTemplate, ObjectMapper mapper) {
        this.redisTemplate = redisTemplate;
        this.mapper = mapper;
    }

    /**
     * Redis lock
     *
     * @param key lock key
     * @param value lock value
     * @return 1: lock success, 0: lock fail
     */
    public Long lock(String key, String value) {
        key = LOCK_PREFIX + key;

        //使用 Jackson 序列化 value
        try {
            value = mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("lua/lock.lua"));
        script.setResultType(Long.class);

        return redisTemplate.execute(script, Arrays.asList(key, value, "5000"));
    }

    /**
     * Redis unlock
     *
     * @param key lock key
     * @param value lock value
     * @return 1: unlock success, 0: unlock fail
     */
    public Long unlock(String key, String value) {
        key = LOCK_PREFIX + key;

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("lua/unlock.lua"));
        script.setResultType(Long.class);

        return redisTemplate.execute(script, Collections.singletonList(key), value);
    }

}
