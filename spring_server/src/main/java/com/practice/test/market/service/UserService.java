package com.practice.test.market.service;

import com.practice.test.market.dto.RatingResponseDTO;
import com.practice.test.market.entity.User;

public interface UserService {
    void saveUser(User user);

    User findById(long id);

    User authorizeByEmailPass(String email, String pass);

    void updateBalanceByUser(long id, double newBalance);



    RatingResponseDTO setScore(long id, int rating, long shopId);
}
