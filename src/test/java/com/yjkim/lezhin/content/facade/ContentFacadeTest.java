package com.yjkim.lezhin.content.facade;

import com.yjkim.lezhin.content.application.service.ContentService;
import com.yjkim.lezhin.contentViewHistory.application.service.ContentViewHistoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ContentFacadeTest {

    @Mock
    private ContentService contentService;

    @Mock
    private ContentViewHistoryService contentViewHistoryService;

    @InjectMocks
    private ContentFacade contentFacade;


    @Test
    @DisplayName("deleteContent()가 정상적으로 실행되는지 테스트")
    void deleteContentTest() {
        Long contentId = 1L;

        contentFacade.deleteContent(contentId);

        verify(contentService, times(1)).deleteContent(contentId);
        verify(contentViewHistoryService, times(1)).deleteContentViewHistory(contentId);
    }
}