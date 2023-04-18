package com.practice.test.market.repository;

import com.practice.test.market.entity.Category;
import com.practice.test.market.entity.Item;
import com.practice.test.market.entity.Platform;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByTitle(String name, Pageable pageable);
    List<Item> findItemsByCategory_NameContainingAndPlatform_NameContainingAndTitleContaining(String category, String platform, String title,
                                                                  Pageable pageable);

    Item findItemById(long id);
    Item findItemByTitle(String title);
    List<Item> findItemsByShop_Id(long shopId);
}
