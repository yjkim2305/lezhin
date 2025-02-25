package com.yjkim.lezhin.contentViewHistory.infrastructure.repository;

import com.yjkim.lezhin.contentViewHistory.infrastructure.entity.ContentViewHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentViewHistoryJpaRepository extends JpaRepository<ContentViewHistoryEntity, Long> {
}
