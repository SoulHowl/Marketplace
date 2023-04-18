package com.practice.test.market.dao;

import com.practice.test.market.entity.Item;
import com.practice.test.market.entity.OrderedItem;

import java.util.ArrayList;
import java.util.List;

public interface ItemDAO {

    List<Item> findAllOrderByTitleAsc(String name, String category, String platform);
    List<Item> findAllOrderByTitleDesc(String name, String category, String platform);
    List<Item> findAllOrderByPriceAsc(String name, String category, String platform);
    List<Item> findAllOrderByPriceDesc(String name, String category, String platform);

    Item findOne(long id);

    void deleteItem(long id);
    void updateItem(Item it);
    void create(Item it);


    long addToCart(OrderedItem odIt, long id);
}
