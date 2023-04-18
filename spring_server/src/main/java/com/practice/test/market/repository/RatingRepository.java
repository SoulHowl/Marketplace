package com.practice.test.market.repository;

import com.practice.test.market.entity.UserScore;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<UserScore, Integer> {

    UserScore getUserScoreByUser_Id(long id);
}
