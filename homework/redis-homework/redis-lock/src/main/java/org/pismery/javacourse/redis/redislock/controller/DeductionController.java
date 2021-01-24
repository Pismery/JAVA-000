package org.pismery.javacourse.redis.redislock.controller;

import org.pismery.javacourse.redis.redislock.util.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("deduction")
public class DeductionController {

    @Autowired
    private RedisLock redisLock;

    @GetMapping("/deductWithoutLock")
    public String deductWithoutLock() throws InterruptedException {
        final Inventory inventory = new Inventory("pis-inventory", 100);

        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            pool.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    //no-lock deduct
                    inventory.deduct();
                }
            });
        }

        pool.awaitTermination(10, TimeUnit.SECONDS);
        return "Current Inventory: " + inventory.getInventory();
    }

    @GetMapping("/deductWithLock")
    public String deductWithLock() throws InterruptedException {
        final Inventory inventory = new Inventory("pis-inventory", 100);

        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            pool.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        while(redisLock.lock("pis-inventory", inventory.getName()) == 1) {
                            inventory.deduct();
                        }
                    } finally {
                        redisLock.unlock("pis-inventory", inventory.getName());
                    }
                }
            });
        }

        pool.awaitTermination(10, TimeUnit.SECONDS);
        return "Current Inventory: " + inventory.getInventory();
    }

    public static class Inventory {
        private int inventory;
        private String name;

        public Inventory(String name, int inventory) {
            this.name = name;
            this.inventory = inventory;
        }

        public void deduct() {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (inventory <= 0) {
                return;
            }

            inventory--;
        }

        public int getInventory() {
            return inventory;
        }
        public String getName() {
            return name;
        }

    }

}
