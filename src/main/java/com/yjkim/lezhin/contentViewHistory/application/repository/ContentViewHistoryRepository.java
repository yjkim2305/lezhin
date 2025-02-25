package com.yjkim.lezhin.contentViewHistory.application.repository;

import com.yjkim.lezhin.contentViewHistory.application.dto.ContentViewResult;
import com.yjkim.lezhin.contentViewHistory.application.dto.TopContentViewResult;
import com.yjkim.lezhin.contentViewHistory.domain.ContentViewHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContentViewHistoryRepository {
    void registerContentViewHistory(ContentViewHistory contentViewHistory);
    Page<ContentViewResult> findContentViewHistories(Long contentId, Pageable pageable);
    List<TopContentViewResult> findTop10ContentViewHistories(Long memberId);
    void deleteContentViewHistory(Long contentId);
}
