package com.yjkim.lezhin.contentViewHistory.infrastructure.repository;

import com.yjkim.lezhin.contentViewHistory.application.repository.ContentViewHistoryRepository;
import com.yjkim.lezhin.contentViewHistory.domain.ContentViewHistory;
import com.yjkim.lezhin.contentViewHistory.infrastructure.entity.ContentViewHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ContentViewHistoryRepositoryImpl implements ContentViewHistoryRepository {
    private final ContentViewHistoryJpaRepository contentViewHistoryJpaRepository;

    @Override
    public void registerContentViewHistory(ContentViewHistory contentViewHistory) {
        contentViewHistoryJpaRepository.save(ContentViewHistoryEntity.toEntity(contentViewHistory));
    }
}
