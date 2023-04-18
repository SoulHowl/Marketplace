package com.practice.test.market.service;

import com.practice.test.market.dao.PurchaseDAO;
import com.practice.test.market.dao.UserDAO;
import com.practice.test.market.dto.CartItemDTO;
import com.practice.test.market.dto.ItemResponseDTO;
import com.practice.test.market.dto.ShopDTO;
import com.practice.test.market.entity.Item;
import com.practice.test.market.entity.OrderedItem;
import com.practice.test.market.entity.Shop;
import com.practice.test.market.entity.UserScore;
import com.practice.test.market.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService{


    private ShopRepository shopRepository;

    private ItemRepository itemRepository;
    private PlatformRepository platformRepository;
    private CategoryRepository categoryRepository;

    private OrderedItemRepository orderedItemRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, ItemRepository itemRepository,
                           PlatformRepository platformRepository,
                           CategoryRepository categoryRepository,
                           OrderedItemRepository orderedItemRepository) {
        this.shopRepository = shopRepository;
        this.itemRepository = itemRepository;
        this.platformRepository = platformRepository;
        this.categoryRepository = categoryRepository;
        this.orderedItemRepository = orderedItemRepository;
    }

    @Override
    @Transactional
    public ItemResponseDTO createItem(ItemResponseDTO item) {
        if(itemRepository.findItemByTitle(item.getTitle()) == null) {
            Item newItem = new Item();
            newItem.setShop(shopRepository.findByName(item.getShop()));
            newItem.setTitle(item.getTitle());
            newItem.setQuantity(item.getQuantity());
            newItem.setPrice(item.getPrice());
            newItem.setHidden(false);
            newItem.setPlatform(platformRepository.findPlatformByName(item.getPlatform()));
            newItem.setCategory(categoryRepository.findCategoryByName(item.getCategory()));
            newItem.setOrderedItems(null);
            newItem.setId(0);
            var it = itemRepository.save(newItem);
            item.setId(it.getId());
            return item;
        }
        return null;
    }

    @Override
    @Transactional
    public ItemResponseDTO updateItem(ItemResponseDTO item) {
        Item newItem = itemRepository.findItemById(item.getId());
        newItem.setQuantity(item.getQuantity());
        newItem.setPrice(item.getPrice());
        newItem.setPlatform(platformRepository.findPlatformByName(item.getPlatform()));
        newItem.setCategory(categoryRepository.findCategoryByName(item.getCategory()));
        itemRepository.save(newItem);
        return item;
    }

    @Override
    public boolean hideItem(ItemResponseDTO item) {
        Item hiddenItem = itemRepository.findItemById(item.getId());
        hiddenItem.setHidden(true);
        var it = itemRepository.save(hiddenItem);
        return it.isHidden();
    }

    @Override
    public String completeOrderedItem(long orderedItemId, String value) {
        OrderedItem odIt = orderedItemRepository.findOrderedItemById(orderedItemId);
        odIt.setValue(value);
        var res = orderedItemRepository.save(odIt);
        return res.getValue();
    }

    @Override
    public ShopDTO getShopItems(long userId) {
        Shop shop = shopRepository.findByUser_Id(userId);
        double score = 0;
        int count = 0;
        for(UserScore userScore: shop.getRates()){
            score += userScore.getRateScore();
            count++;
        }
        score = count !=0 ? score / count : 0;
        ShopDTO shopDTO = new ShopDTO(shop.getId(), shop.getName(),
                score != 0?String.valueOf(score):"N/A",null,shop.getUser().getId(), null);
        long shopId = shop.getId();
        List<Item> items = itemRepository.findItemsByShop_Id(shopId);
        List<ItemResponseDTO> itemResponseDTOS = new ArrayList<>();
        for(Item item: items){
            itemResponseDTOS.add(new ItemResponseDTO(item.getId(), item.getShop().getName(), item.getCategory().getName(),
                    item.getPlatform().getName(), item.getTitle(), item.getDescription(), item.getQuantity(), item.getPrice()));
        }
        shopDTO.setItems(itemResponseDTOS);
        return shopDTO;
    }

    @Override
    public List<CartItemDTO> getOrdersForShop(long shopId, boolean completed) {
        return null;
    }
}
