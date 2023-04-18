package com.practice.test.market.service;

import com.practice.test.market.dto.CartDTO;
import com.practice.test.market.dto.CartItemDTO;
import com.practice.test.market.entity.Order;
import com.practice.test.market.entity.OrderedItem;

import java.util.List;

public interface PurchaseService {

    CartDTO getCart(long id);
    void deleteCartItem(long id);
    boolean checkDeleted(long id);

    long updateCartItemQuantity(CartItemDTO odItem);

    boolean makeOrder(long id);

    List<CartDTO> getUserHistory(long id);

}
