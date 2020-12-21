package org.pismery.javacourse.rpc.demo.provider;

import org.pismery.javacourse.rpc.demo.api.Order;
import org.pismery.javacourse.rpc.demo.api.OrderService;

public class OrderServiceImpl implements OrderService {

    @Override
    public Order findOrderById(int id) {
        return new Order(id, "Cuijing" + System.currentTimeMillis(), 9.9f);
    }
}
