package org.pismery.javacourse.database.homework.controller;

import org.pismery.javacourse.database.homework.entity.Order;
import org.pismery.javacourse.database.homework.sevice.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping(value = "/order", produces = "application/json")
    public String insert(@RequestBody Order order) {
        orderService.save(order);
        return "Save successfully";
    }

    @GetMapping("/order")
    public Order selectByPrimaryKey(@RequestParam Long id) {
        return orderService.selectById(id);
    }

}
