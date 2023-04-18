package com.practice.test.market.dao;

import com.practice.test.market.entity.Item;
import com.practice.test.market.entity.Order;
import com.practice.test.market.entity.OrderedItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {
    private EntityManager entityManager;

    @Autowired
    public OrderDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Order> findAll() {
        TypedQuery<Order> theQuery = entityManager.createQuery
                ("From Order", Order.class);
        return theQuery.getResultList();
    }
}
