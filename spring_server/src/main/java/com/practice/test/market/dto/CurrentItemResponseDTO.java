package com.practice.test.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrentItemResponseDTO {
    private long id;
    private ShopDTO shop;
    private String category;
    private String platform;
    private String title;
    private String description;
    private long quantity;
    private double price;
    private boolean inCart;
}
