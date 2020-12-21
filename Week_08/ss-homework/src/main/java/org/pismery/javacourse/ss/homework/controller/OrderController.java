package org.pismery.javacourse.ss.homework.controller;

import org.pismery.javacourse.ss.homework.entity.Order;
import org.pismery.javacourse.ss.homework.sevice.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping(value = "/order", produces = "application/json")
    public Order insert(@RequestBody Order order) {
        return orderService.save(order);
    }

    @GetMapping("/order")
    public Order selectByPrimaryKey(@RequestParam Long id) {
        return orderService.selectById(id);
    }

    @DeleteMapping("/order")
    public int deleteByPrimaryKey(@RequestBody Order order) {
        return orderService.deleteById(order.getId());
    }

    @PutMapping("/order")
    public int updateByPrimaryKey(@RequestBody Order order) {
        return orderService.updateByPrimaryKey(order);
    }

}
