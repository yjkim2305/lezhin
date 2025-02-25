package com.yjkim.lezhin.common.event;

import com.yjkim.lezhin.contentViewHistory.application.service.ContentViewHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContentViewEventListener {
    private final ContentViewHistoryService contentViewHistoryService;

    @Async
    @EventListener
    public void registerContentViewHistory(ContentViewEvent event) {
        contentViewHistoryService.registerContentViewHistory(event.contentId(), event.memberId());
    }
}
