package com.practice.test.market.dto;

import com.practice.test.market.entity.Role;
import com.practice.test.market.entity.UserScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
public class RegisterDTO {

    private String nickname;

    private String email;
    private String password;

    private double balance;

    private Set<Role> roles;
    private String logo;
    private List<UserScore> rates ;
}
