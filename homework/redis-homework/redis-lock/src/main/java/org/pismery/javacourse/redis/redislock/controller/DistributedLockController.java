package org.pismery.javacourse.redis.redislock.controller;

import org.pismery.javacourse.redis.redislock.util.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("distributedLock")
public class DistributedLockController {

    @Autowired
    private RedisLock redisLock;

    @GetMapping(value = "/lock", produces = "application/json")
    public Long lock() {
        return redisLock.lock("lock1", "pis");
    }

    @GetMapping(value = "/unlock", produces = "application/json")
    public Long unlock() {
        return redisLock.unlock("lock1", "pis");
    }
}
