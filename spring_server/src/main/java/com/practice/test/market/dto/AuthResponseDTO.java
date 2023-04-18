package com.practice.test.market.dto;

import com.practice.test.market.entity.User;
import lombok.Data;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";
    private UserResponseDTO user;

    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
