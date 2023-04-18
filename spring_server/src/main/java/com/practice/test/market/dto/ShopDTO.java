package com.practice.test.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopDTO {

    private long id;
    private String name;
    private String score;
    private String myScore;
    private long sellerId;

    private List<ItemResponseDTO> items;
}
