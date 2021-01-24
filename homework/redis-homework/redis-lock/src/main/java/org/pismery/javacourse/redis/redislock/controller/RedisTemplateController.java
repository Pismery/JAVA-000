package org.pismery.javacourse.redis.redislock.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redisTemplate")
public class RedisTemplateController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @GetMapping("/{key}")
    public String get(@PathVariable String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    @PutMapping(value = "/{key}", produces = "application/json")
    public String put(@PathVariable String key
            , @RequestHeader String headerValue
            , @RequestBody String bodyValue
            , @RequestParam String paramValue) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key + "-header", headerValue);
        operations.set(key + "-param", paramValue);
        operations.set(key + "-body", bodyValue);
        return "success";
    }

}
