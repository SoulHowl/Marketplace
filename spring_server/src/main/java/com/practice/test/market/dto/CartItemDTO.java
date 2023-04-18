package com.practice.test.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {
    private long id;
    private String shop;
    private String title;
    private String description;
    private long quantity;
    private double price;
    private String status;
    private String value;

}
