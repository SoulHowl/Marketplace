package com.practice.test.market.dao;

import com.practice.test.market.entity.Order;
import com.practice.test.market.entity.OrderedItem;
import com.practice.test.market.entity.User;

import java.util.List;

public interface PurchaseDAO {

    void createCart(User user);

    void updateCartItemQuantity(OrderedItem odIt);

    void deleteItemFromCart(long id);

    Order getCartByUser(long id);

    void makeOrder(long id);

    List<Order> getOrdersByUser(long id);
}
