package com.yjkim.lezhin.contentViewHistory.infrastructure.repository;

import com.yjkim.lezhin.contentViewHistory.application.dto.ContentViewResult;
import com.yjkim.lezhin.contentViewHistory.application.dto.TopContentViewResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomContentViewHistoryJpaRepository {
    Page<ContentViewResult> findContentViewHistories(Long contentId, Pageable pageable);
    List<TopContentViewResult> findTop10ContentViewHistories(Long memberId);
}
