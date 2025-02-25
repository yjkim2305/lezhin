package com.yjkim.lezhin.contentViewHistory.application.repository;

import com.yjkim.lezhin.contentViewHistory.domain.ContentViewHistory;

public interface ContentViewHistoryRepository {
    void registerContentViewHistory(ContentViewHistory contentViewHistory);
}
