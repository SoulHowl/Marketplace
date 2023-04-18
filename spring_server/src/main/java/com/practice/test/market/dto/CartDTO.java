package com.practice.test.market.dto;

import com.practice.test.market.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {

    private long id;
    private String status;
    private List<CartItemDTO> items;
    private double sum;
    private String datePurchase;

}
