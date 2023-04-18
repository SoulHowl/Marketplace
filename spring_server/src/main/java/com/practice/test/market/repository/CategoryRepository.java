package com.practice.test.market.repository;

import com.practice.test.market.entity.Category;
import com.practice.test.market.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findCategoryByName(String name);
}
