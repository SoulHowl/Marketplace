package com.practice.test.market.dao;

import com.practice.test.market.entity.Order;
import com.practice.test.market.entity.OrderedItem;

import java.util.List;

public interface OrderDAO {
    List<Order> findAll();
}
