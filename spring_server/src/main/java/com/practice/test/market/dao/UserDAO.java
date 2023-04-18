package com.practice.test.market.dao;

import com.practice.test.market.entity.User;

public interface UserDAO {
    User saveUser(User user);

    User findById(long id);

    User findByNickname(String nickname);

    User authorizeByEmailPass(String email, String pass);

    void topUpBalance(long id, double newBalance);

}
