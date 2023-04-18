package com.practice.test.market.dto;

import com.practice.test.market.entity.UserScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private long id;
    private String nickname;

    private String email;

    private double balance;

    private List<String> roles;

}
