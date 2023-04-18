package com.practice.test.market.service;

import com.practice.test.market.dao.ItemDAO;
import com.practice.test.market.dto.*;
import com.practice.test.market.entity.*;
import com.practice.test.market.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    public int page=0;
    public int per=0;
    private ItemDAO itemDAO;
    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;
    private PlatformRepository platformRepository;

    private ShopRepository shopRepository;

    private OrderRepository orderRepository;

    public ItemServiceImpl(ItemDAO itemDAO, ItemRepository itemRepository, CategoryRepository categoryRepository, PlatformRepository platformRepository, ShopRepository shopRepository, OrderRepository orderRepository) {
        this.itemDAO = itemDAO;
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.platformRepository = platformRepository;
        this.shopRepository = shopRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public ItemResponseListDTO findAll(String title, String category, String platform, PageRequest pageRequest, int pageNo, int pageSize) {

          List<Item> items = itemRepository.
                  findItemsByCategory_NameContainingAndPlatform_NameContainingAndTitleContaining(
                          category, platform, title, pageRequest);
        ItemResponseListDTO listResult = new ItemResponseListDTO();
        List<ItemResponseDTO> its = new ArrayList<>();
        for (Item it:items){
            its.add(new ItemResponseDTO(it.getId(),it.getShop().getName(), it.getCategory().getName(),
                    it.getPlatform().getName(), it.getTitle(), it.getDescription(), it.getQuantity(),
                    it.getPrice()));

        }
        listResult.setItems(its);
        listResult.setPageNo(pageNo);
        listResult.setPageSize(pageSize);
        listResult.setTotalPages(pageNo+1);
        listResult.setTotalElements((long) pageNo * pageSize + listResult.getItems().size());
        listResult.setLast(listResult.getItems().size() % pageSize < pageSize);
//        if(byPrice){
//            if (ascending){
//                return itemDAO.findAllOrderByPriceAsc(title, category, platform);
//            }
//            else {
//                return itemDAO.findAllOrderByPriceDesc(title, category, platform);
//            }
//        }
//        if (byTitle){
//            if(ascending){
//                return itemDAO.findAllOrderByTitleAsc(title, category, platform);
//            }
//            else{
//                return itemDAO.findAllOrderByTitleDesc(title, category, platform);
//            }
//        }
//        return null;
        return listResult;
    }

    @Override
    public CurrentItemResponseDTO getItem(long id, long userId) {
        Item it =itemRepository.findItemById(id);
        CurrentItemResponseDTO currentItemResponseDTO = new CurrentItemResponseDTO();
        currentItemResponseDTO.setId(it.getId());
        double score = 0;
        double myScore = 0;
        var count = 0;
        var rates = it.getShop().getRates();
        for(UserScore us: rates){
            score += us.getRateScore();
            if(us.getUser().getId() == userId){
                myScore = us.getRateScore();
            }
            count++;
        }
        score = count== 0?0: score/count;
        currentItemResponseDTO.setShop(new ShopDTO(it.getShop().getId(),
                it.getShop().getName(), score!=0?String.valueOf(score):"N/A",
                myScore == 0 ? "N/A" : String.valueOf(myScore), it.getShop().getUser().getId(), null));
        currentItemResponseDTO.setPrice(it.getPrice());
        currentItemResponseDTO.setQuantity(it.getQuantity());
        currentItemResponseDTO.setCategory(it.getCategory().getName());
        currentItemResponseDTO.setPlatform(it.getPlatform().getName());
        currentItemResponseDTO.setTitle(it.getTitle());
        currentItemResponseDTO.setDescription(it.getDescription());

        Order order = orderRepository.findOrderByUser_IdAndStatus_TypeEquals(userId, "inventory");
        var inCart = false;
        for(OrderedItem odIt:order.getOrderedItems()){
           if(odIt.getItem().getId() == id) {inCart = true; break;}
        }
        currentItemResponseDTO.setInCart(inCart);

        return currentItemResponseDTO;
    }

    @Override
    @Transactional
    public long setItem(ItemResponseDTO it, long userId) {
        Item item = new Item();
        item.setCategory(categoryRepository.findCategoryByName(it.getCategory()));
        item.setDescription(it.getDescription());
        item.setHidden(false);
        item.setPlatform(platformRepository.findPlatformByName(it.getPlatform()));
        item.setPrice(it.getPrice());
        item.setQuantity(it.getQuantity());
        item.setTitle(it.getTitle());
        item.setShop(shopRepository.findByUser_Id(userId));
        // insert title check

        itemRepository.save(item);
        return -999;
    }

    @Override
    public void updateItem(Item item) {

    }

    @Override
    public void deleteItem(long id) {

    }

    @Override
    public long addItemToCart(long itemId, long userId, long quantity) {
        var item = itemDAO.findOne(itemId);
        var odIt = new OrderedItem();
        odIt.setItem(item);
        odIt.setPrice(item.getPrice());
        odIt.setQuantity(quantity);

         return itemDAO.addToCart(odIt, userId);

    }
}
