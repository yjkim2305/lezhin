package com.yjkim.lezhin.contentViewHistory.application.service;

import com.yjkim.lezhin.contentViewHistory.application.dto.ContentViewResult;
import com.yjkim.lezhin.contentViewHistory.application.dto.TopContentViewResult;
import com.yjkim.lezhin.contentViewHistory.application.repository.ContentViewHistoryRepository;
import com.yjkim.lezhin.contentViewHistory.domain.ContentViewHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentViewHistoryService {
    private final ContentViewHistoryRepository contentViewHistoryRepository;

    public void registerContentViewHistory(Long contentId, Long memberId) {
        contentViewHistoryRepository.registerContentViewHistory(ContentViewHistory.of(contentId, memberId));
    }

    public Page<ContentViewResult> findContentViewHistory(Long contentId, Pageable pageable) {
        return contentViewHistoryRepository.findContentViewHistories(contentId, pageable);
    }

    public List<TopContentViewResult> findTop10ContentViewHistories(Long memberId) {
        return contentViewHistoryRepository.findTop10ContentViewHistories(memberId);
    }

    public void deleteContentViewHistory(Long contentId) {
        contentViewHistoryRepository.deleteContentViewHistory(contentId);
    }
}
