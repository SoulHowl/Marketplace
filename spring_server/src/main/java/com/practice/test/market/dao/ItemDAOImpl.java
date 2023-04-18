package com.practice.test.market.dao;

import com.practice.test.market.entity.Item;
import com.practice.test.market.entity.Order;
import com.practice.test.market.entity.OrderedItem;
import com.practice.test.market.entity.OrderedItemStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDAOImpl implements ItemDAO{
    private EntityManager entityManager;

    @Autowired
    public ItemDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Item> findAllOrderByTitleAsc(String name, String category, String platform) {
        TypedQuery<Item> theQuery = entityManager.createQuery
                ("From Item where title like concat('%',:theName,'%') " +
                        "and platform.name like concat('%',:thePlatform,'%') " +
                        "and category.name like concat('%',:theCategory,'%') " +
                        "order by title asc "
                        , Item.class);
        theQuery.setParameter("theName", name);
        theQuery.setParameter("thePlatform", platform);
        theQuery.setParameter("theCategory", category);

        return theQuery.getResultList();
    }

    @Override
    public List<Item> findAllOrderByTitleDesc(String name, String category, String platform) {
        TypedQuery<Item> theQuery = entityManager.createQuery
                ("From Item where title like concat('%',:theName,'%') " +
                        "and platform.name like concat('%',:thePlatform,'%') " +
                        "and category.name like concat('%',:theCategory,'%') order by title desc", Item.class);
        theQuery.setParameter("theName", name);
        theQuery.setParameter("thePlatform", platform);
        theQuery.setParameter("theCategory", category);

        return theQuery.getResultList();
    }

    @Override
    public List<Item> findAllOrderByPriceAsc(String name, String category, String platform) {
        TypedQuery<Item> theQuery = entityManager.createQuery
                ("From Item where title like concat('%',:theName,'%') " +
                        "and platform.name like concat('%',:thePlatform,'%') " +
                        "and category.name like concat('%',:theCategory,'%') order by price asc ", Item.class);
        theQuery.setParameter("theName", name);
        theQuery.setParameter("thePlatform", platform);
        theQuery.setParameter("theCategory", category);

        return theQuery.getResultList();
    }

    @Override
    public List<Item> findAllOrderByPriceDesc(String name, String category, String platform) {
        TypedQuery<Item> theQuery = entityManager.createQuery
                ("From Item where title like concat('%',:theName,'%') " +
                        "and platform.name like concat('%',:thePlatform,'%') " +
                        "and category.name like concat('%',:theCategory,'%') order by price desc ", Item.class);
        theQuery.setParameter("theName", name);
        theQuery.setParameter("thePlatform", platform);
        theQuery.setParameter("theCategory", category);

        return theQuery.getResultList();
    }

    @Override
    public Item findOne(long id) {
        return entityManager.find(Item.class, id);
    }

    @Override
    @Transactional
    public void deleteItem(long id) {
        entityManager.remove(id);
    }

    @Override
    @Transactional
    public void updateItem(Item it) {
        entityManager.merge(it);
    }

    @Override
    @Transactional
    public void create(Item it) {
        entityManager.persist(it);
    }

    @Override
    @Transactional
    public long addToCart(OrderedItem odIt, long id) {
        var order = entityManager.createQuery
                ("From Order where user.id = :userId", Order.class).setParameter("userId", id).getSingleResult();
        odIt.setOrder(order);
        odIt.setOrderedItemStatus(entityManager.createQuery
                ("From OrderedItemStatus where type='inventory'", OrderedItemStatus.class).getSingleResult());
        entityManager.persist(odIt);
       OrderedItem odIt2 = entityManager
               .createQuery("From OrderedItem where item = :it and order.id=:odid",OrderedItem.class)
               .setParameter("it", odIt.getItem())
               .setParameter("odid",order.getId())
               .getSingleResult();
       return odIt2.getId();
    }
}
// ("From Item where title like cocncat('%',:theData,'%')"