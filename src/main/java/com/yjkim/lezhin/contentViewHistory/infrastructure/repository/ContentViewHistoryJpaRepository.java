package com.yjkim.lezhin.contentViewHistory.infrastructure.repository;

import com.yjkim.lezhin.contentViewHistory.infrastructure.entity.ContentViewHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ContentViewHistoryJpaRepository extends JpaRepository<ContentViewHistoryEntity, Long>, CustomContentViewHistoryJpaRepository {
    @Modifying
    @Transactional
    @Query("DELETE FROM ContentViewHistoryEntity c WHERE c.contentId = :contentId")
    void deleteByContentId(@Param("contentId") Long contentId);


}
