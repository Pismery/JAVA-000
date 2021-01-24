package org.pismery.javacourse.redis.redislock.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisSerializerConfig {
    @Bean("jackson2JsonRedisSerializer")
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(ObjectMapper objectMapper) {
        Jackson2JsonRedisSerializer<Object> result = new Jackson2JsonRedisSerializer<>(Object.class);
        result.setObjectMapper(objectMapper);
        return result;
    }

    @Bean("stringRedisSerializer")
    public StringRedisSerializer StringRedisSerializer() {
        return new StringRedisSerializer();
    }

}
