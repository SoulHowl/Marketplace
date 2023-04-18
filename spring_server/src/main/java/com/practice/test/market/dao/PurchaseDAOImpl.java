package com.practice.test.market.dao;

import com.practice.test.market.entity.Order;
import com.practice.test.market.entity.OrderedItem;
import com.practice.test.market.entity.Status;
import com.practice.test.market.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public class PurchaseDAOImpl implements PurchaseDAO {
    private EntityManager entityManager;

    @Autowired
    public PurchaseDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void createCart(User user) {
        Order newInventory = new Order();
        newInventory.setStatus(entityManager.createQuery
                ("From Status where type='inventory'", Status.class).getSingleResult());
        newInventory.setSum(0.0);
        newInventory.setUser(user);
        entityManager.merge(newInventory);
    }

    @Override
    @Transactional
    public void updateCartItemQuantity(OrderedItem odIt) {
        entityManager.merge(odIt);
    }

    @Override
    @Transactional
    public void deleteItemFromCart(long id) {
        entityManager.remove(id);
    }

    @Override
    public Order getCartByUser(long id) {
        return entityManager.createQuery
                ("From Order where status.type='inventory' and user.id=:userid", Order.class)
                .setParameter("userid", id)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void makeOrder(long id) {
        Order inventory = entityManager.createQuery
                ("From Order where status.type='inventory'", Order.class).getSingleResult();
        User me = inventory.getUser();
        me.setBalance(me.getBalance() - inventory.getSum());

        entityManager.merge(me);
        Order newInventory = new Order();
        newInventory.setStatus(inventory.getStatus());
        inventory.setStatus(entityManager.createQuery
                ("From Status where Status.type='purchased'", Status.class).getSingleResult());
        inventory.setPurchaseDate(new Date());
        entityManager.merge(inventory);
        newInventory.setSum(0.0);
        newInventory.setUser(me);
        entityManager.persist(newInventory);
    }

    @Override
    public List<Order> getOrdersByUser(long id) {
        TypedQuery<Order> theQuery = entityManager.createQuery
                ("From Order where status.type='inventory'", Order.class);
        return theQuery.getResultList();
    }
}
