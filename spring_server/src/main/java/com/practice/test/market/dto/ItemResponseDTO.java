package com.practice.test.market.dto;

import com.practice.test.market.entity.Category;
import com.practice.test.market.entity.Platform;
import com.practice.test.market.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponseDTO {

    private long id;
    private String shop;
    private String category;
    private String platform;
    private String title;
    private String description;
    private long quantity;
    private double price;


}
