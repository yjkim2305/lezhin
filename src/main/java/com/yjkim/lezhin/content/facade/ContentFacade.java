package com.yjkim.lezhin.content.facade;

import com.yjkim.lezhin.content.application.service.ContentService;
import com.yjkim.lezhin.contentViewHistory.application.service.ContentViewHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ContentFacade {
    private final ContentService contentService;
    private final ContentViewHistoryService contentViewHistoryService;

    @Transactional
    public void deleteContent(Long contentId) {
        contentService.deleteContent(contentId);
        contentViewHistoryService.deleteContentViewHistory(contentId);
    }
}
