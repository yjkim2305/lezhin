package com.yjkim.lezhin.refresh.infrastructure.repository;

import com.yjkim.lezhin.refresh.infrastructure.entity.RefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshJpaRepository extends JpaRepository<RefreshEntity, Long> {
    Boolean existsByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);
}
