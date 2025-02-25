package com.yjkim.lezhin.contentViewHistory.application.repository;

import com.yjkim.lezhin.contentViewHistory.application.dto.ContentViewResult;
import com.yjkim.lezhin.contentViewHistory.domain.ContentViewHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentViewHistoryRepository {
    void registerContentViewHistory(ContentViewHistory contentViewHistory);

    Page<ContentViewResult> findContentViewHistories(Long contentId, Pageable pageable);
}
