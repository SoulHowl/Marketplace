package com.practice.test.market.repository;

import com.practice.test.market.entity.OrderedItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedItemStatusRepository extends JpaRepository<OrderedItemStatus, Integer> {
    OrderedItemStatus findByType(String type);
}
