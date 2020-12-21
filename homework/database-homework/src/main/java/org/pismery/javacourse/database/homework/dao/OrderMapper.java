package org.pismery.javacourse.database.homework.dao;

import org.pismery.javacourse.database.homework.entity.Order;

public interface OrderMapper {

    int insert(Order record);

    Order selectByPrimaryKey(Long id);
}
