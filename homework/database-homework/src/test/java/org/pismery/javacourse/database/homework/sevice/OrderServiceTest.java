package org.pismery.javacourse.database.homework.sevice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.pismery.javacourse.database.homework.DataBaseHomeworkApplication;
import org.pismery.javacourse.database.homework.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = DataBaseHomeworkApplication.class)
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    void save() {
        Order order = new Order();
        order.setId(4L);
        orderService.save(order);
    }

    @Test
    void selectById() {
        Order order = orderService.selectById(5L);
        order = orderService.selectById(5L);
        order = orderService.selectById(5L);
        order = orderService.selectById(5L);
        log.info(order.toString());
    }
}