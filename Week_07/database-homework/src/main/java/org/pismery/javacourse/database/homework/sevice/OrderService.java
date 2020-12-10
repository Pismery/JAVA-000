package org.pismery.javacourse.database.homework.sevice;

import org.pismery.javacourse.database.homework.dao.OrderMapper;
import org.pismery.javacourse.database.homework.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {


    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    public void save(final Order t1) {
        orderMapper.insert(t1);
    }

    @Transactional(readOnly = true)
    public Order selectById(final Long id) {
        return orderMapper.selectByPrimaryKey(id);
    }

}
