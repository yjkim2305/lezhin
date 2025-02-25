package com.yjkim.lezhin.contentViewHistory.application.service;

import com.yjkim.lezhin.contentViewHistory.application.repository.ContentViewHistoryRepository;
import com.yjkim.lezhin.contentViewHistory.domain.ContentViewHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentViewHistoryService {
    private final ContentViewHistoryRepository contentViewHistoryRepository;

    public void registerContentViewHistory(Long contentId, Long memberId) {
        contentViewHistoryRepository.registerContentViewHistory(ContentViewHistory.of(contentId, memberId));
    }
}
