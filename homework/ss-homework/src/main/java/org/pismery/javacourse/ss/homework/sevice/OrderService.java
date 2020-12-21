package org.pismery.javacourse.ss.homework.sevice;

import org.pismery.javacourse.ss.homework.dao.OrderMapper;
import org.pismery.javacourse.ss.homework.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {


    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    public Order save(final Order t1) {
        orderMapper.insert(t1);
        return t1;
    }

    @Transactional(readOnly = true)
    public Order selectById(final Long id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public int deleteById(final Long id) {
        return orderMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public int updateByPrimaryKey(final Order order) {
        return orderMapper.updateByPrimaryKey(order);
    }

}
