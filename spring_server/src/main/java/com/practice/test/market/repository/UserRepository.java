package com.practice.test.market.repository;

import com.practice.test.market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByNickname(String nickname);
    Boolean existsByNickname(String nickname);
    Boolean existsByEmail(String email);

    User findById(long id);



}
