package com.practice.test.market.repository;

import com.practice.test.market.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {

    Status findStatusByType(String type);
}
