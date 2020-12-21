package org.pismery.javacourse.ss.homework.entity;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class Order {
    private Long id;
    private Long userId;
    private Long productId;
    private BigDecimal price;
    private String status;
    private Integer payType;
    private String country;
    private String province;
    private String city;
    private String street;
    private String detail;
    private String recipient;
    private String recipientPhone;
    private String recordStatus;
    private Long recordChangedTime;
    private String recordChangedBy;
}

