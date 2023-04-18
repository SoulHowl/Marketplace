package com.practice.test.market.repository;

import com.practice.test.market.entity.Item;
import com.practice.test.market.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findOrderByUser_IdAndStatus_TypeEquals(long userId, String status);

    List<Order> findOrdersByUser_IdAndStatus_Type(long id, String status);
}
