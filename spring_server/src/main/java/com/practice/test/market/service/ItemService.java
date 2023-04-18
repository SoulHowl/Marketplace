package com.practice.test.market.service;

import com.practice.test.market.dto.CurrentItemResponseDTO;
import com.practice.test.market.dto.ItemResponseDTO;
import com.practice.test.market.dto.ItemResponseListDTO;
import com.practice.test.market.entity.Item;
import com.practice.test.market.entity.OrderedItem;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ItemService {

     ItemResponseListDTO findAll(String title, String category, String platform, PageRequest pageRequest,int pageNo, int pageSize);/*,
                        boolean ascending, boolean byTitle, boolean byPrice );*/

     CurrentItemResponseDTO getItem(long id, long userId);

     long setItem(ItemResponseDTO it, long userId);
     void updateItem(Item item);
     void deleteItem(long id);

     long addItemToCart(long itemId, long userId, long quantity);
}
