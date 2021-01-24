package org.pismery.javacourse.redis.redislock.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@Slf4j
public class JedisConfig {
    private final RedisProperties redisProperties;

    public JedisConfig(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Bean
    public JedisPool redisPoolFactory() {
        RedisProperties.Pool pool = redisProperties.getJedis().getPool();

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(pool.getMaxIdle());
        poolConfig.setMinIdle(pool.getMinIdle());
        poolConfig.setMaxTotal(pool.getMaxActive());
        poolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());

        return new JedisPool(poolConfig
                , redisProperties.getHost()
                , redisProperties.getPort()
                , redisProperties.getPort()
                , redisProperties.getPassword()
                , redisProperties.getDatabase()
        );
    }
}
