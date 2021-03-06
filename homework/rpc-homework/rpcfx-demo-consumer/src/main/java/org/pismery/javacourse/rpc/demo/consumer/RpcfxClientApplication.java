package org.pismery.javacourse.rpc.demo.consumer;

import org.pismery.javacourse.rpc.core.client.ByteBuddyRpcfx;
import org.pismery.javacourse.rpc.core.client.CglibRpcfx;
import org.pismery.javacourse.rpc.core.client.JDKRpcfx;
import org.pismery.javacourse.rpc.demo.api.Order;
import org.pismery.javacourse.rpc.demo.api.OrderService;
import org.pismery.javacourse.rpc.demo.api.User;
import org.pismery.javacourse.rpc.demo.api.UserService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpcfxClientApplication {

    // 二方库
    // 三方库 lib
    // nexus, userserivce -> userdao -> user
    //

    public static void main(String[] args) {
        UserService userService = JDKRpcfx.create(UserService.class, "http://localhost:8080/");
        User user = userService.findById(1);
        System.out.println("find user id=1 from server: " + user.getName());

        OrderService orderService = CglibRpcfx.create(OrderService.class, "http://localhost:8080/");
        Order order = orderService.findOrderById(1992129);
        System.out.println(String.format("find order name=%s, amount=%f", order.getName(), order.getAmount()));

        OrderService orderServiceBuddy = ByteBuddyRpcfx.create(OrderService.class, "http://localhost:8080/");
        order = orderServiceBuddy.findOrderById(1992129);
        System.out.println(String.format("find order name=%s, amount=%f", order.getName(), order.getAmount()));
    }

}
