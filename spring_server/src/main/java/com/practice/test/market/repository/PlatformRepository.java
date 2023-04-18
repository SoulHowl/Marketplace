package com.practice.test.market.repository;

import com.practice.test.market.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepository extends JpaRepository<Platform, Integer> {

    Platform findPlatformByName(String name);
}
