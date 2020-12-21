package org.pismery.javacourse.rpc.demo.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.pismery.javacourse.rpc.core.api.ApiBean;

import java.io.IOException;

public class Order implements ApiBean {

    private int id;

    private String name;

    private float amount;

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Order order = new Order(1, "KK", 1F);
        String kk = objectMapper.writeValueAsString(order);
        System.out.println(kk);

        System.out.println(objectMapper.readValue(kk, Order.class));
    }

    public Order() {
    }

    public Order(int id, String name, float amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
