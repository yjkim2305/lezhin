package com.yjkim.lezhin.content.application.service;

import com.yjkim.lezhin.common.event.ContentViewEvent;
import com.yjkim.lezhin.content.application.dto.ContentCreateCommand;
import com.yjkim.lezhin.content.application.dto.ContentResultDto;
import com.yjkim.lezhin.content.application.repository.ContentRepository;
import com.yjkim.lezhin.content.domain.Content;
import com.yjkim.lezhin.content.domain.enums.ContentType;
import com.yjkim.lezhin.content.domain.enums.PriceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest {
    @Mock
    private ContentRepository contentRepository;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private ContentService contentService;

    @Test
    @DisplayName("콘텐츠 등록 시 Repository에 저장 요청이 정상적으로 호출되어야 한다")
    void testRegisterContent() {
        ContentCreateCommand command = new ContentCreateCommand("Test Title", "Test Author", ContentType.ADULT, PriceType.FREE, 10);

        contentService.registerContent(command);

        verify(contentRepository, times(1)).registerContent(any(Content.class));
    }

    @Test
    @DisplayName("콘텐츠를 조회하면 ContentResultDto를 반환해야 한다")
    void testGetContent() {
        Long contentId = 1L;
        Long memberId = 100L;
        boolean isAdult = true;

        Content content = Content.builder()
                .contentType(ContentType.GENERAL)
                .build();

        when(contentRepository.getContent(contentId)).thenReturn(content);

        ContentResultDto result = contentService.getContent(contentId, memberId, isAdult);

        assertThat(result).isNotNull();
        assertThat(result.contentType()).isEqualTo(ContentType.GENERAL);
        verify(contentRepository, times(1)).getContent(contentId);
        verify(applicationEventPublisher, times(1)).publishEvent(any(ContentViewEvent.class));
    }

}