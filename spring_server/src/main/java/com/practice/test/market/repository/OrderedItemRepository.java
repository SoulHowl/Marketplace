package com.practice.test.market.repository;

import com.practice.test.market.entity.Item;
import com.practice.test.market.entity.OrderedItem;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderedItemRepository extends JpaRepository<OrderedItem, Integer> {

    OrderedItem findOrderedItemById(long id);

    void deleteOrderedItemById(long id);

}
