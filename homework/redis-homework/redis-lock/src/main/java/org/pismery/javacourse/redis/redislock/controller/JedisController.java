package org.pismery.javacourse.redis.redislock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/jedis")
public class JedisController {

    @Autowired
    private JedisPool jedisPool;

    @GetMapping("/{key}")
    public String get(@PathVariable String key) {
        Jedis jedis = jedisPool.getResource();
        return jedis.get(key);
    }

    @PutMapping(value = "/{key}", produces = "application/json")
    public String put(@PathVariable String key
            , @RequestHeader String headerValue
            , @RequestBody String bodyValue
            , @RequestParam String paramValue) {
        Jedis jedis = jedisPool.getResource();

        jedis.set(key + "_header", headerValue);
        jedis.set(key + "_param", paramValue);
        jedis.set(key + "_body", bodyValue);
        return "success";
    }

    @GetMapping(value = "/info", produces = "application/json")
    public String run() {
        return jedisPool.getResource().info();
    }
}
