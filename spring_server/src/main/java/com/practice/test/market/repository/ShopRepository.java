package com.practice.test.market.repository;

import com.practice.test.market.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Integer> {

    Shop findById(long id);

    Shop findByUser_Id(long id);

    Shop findByName(String name);

}
