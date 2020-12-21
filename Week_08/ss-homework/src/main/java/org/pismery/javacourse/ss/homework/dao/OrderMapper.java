package org.pismery.javacourse.ss.homework.dao;

import org.pismery.javacourse.ss.homework.entity.Order;

public interface OrderMapper {

    int insert(Order record);

    Order selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Order order);
}
