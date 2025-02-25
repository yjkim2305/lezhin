package com.yjkim.lezhin.contentViewHistory.infrastructure.repository;

import com.yjkim.lezhin.contentViewHistory.application.dto.ContentViewResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomContentViewHistoryJpaRepository {
    Page<ContentViewResult> findContentViewHistories(Long contentId, Pageable pageable);
}
