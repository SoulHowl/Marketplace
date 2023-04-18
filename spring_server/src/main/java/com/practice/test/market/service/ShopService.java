package com.practice.test.market.service;

import com.practice.test.market.dto.CartItemDTO;
import com.practice.test.market.dto.ItemResponseDTO;
import com.practice.test.market.dto.ShopDTO;
import com.practice.test.market.entity.Item;
import com.practice.test.market.entity.OrderedItem;

import java.util.List;

public interface ShopService {

    ItemResponseDTO createItem(ItemResponseDTO item);
    ItemResponseDTO updateItem(ItemResponseDTO item);

    boolean hideItem(ItemResponseDTO item);

    String completeOrderedItem(long id, String value);

    ShopDTO getShopItems(long shopId);

    List<CartItemDTO> getOrdersForShop(long shopId, boolean completed);
}
