package com.yjkim.lezhin.contentViewHistory.application.service;

import com.yjkim.lezhin.contentViewHistory.application.dto.ContentViewResult;
import com.yjkim.lezhin.contentViewHistory.application.dto.TopContentViewResult;
import com.yjkim.lezhin.contentViewHistory.application.repository.ContentViewHistoryRepository;
import com.yjkim.lezhin.contentViewHistory.domain.ContentViewHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContentViewHistoryServiceTest {

    @Mock
    private ContentViewHistoryRepository contentViewHistoryRepository;

    @InjectMocks
    private ContentViewHistoryService contentViewHistoryService;

    @Test
    @DisplayName("작품 조회 이력 등록 테스트")
    void registerContentViewHistoryTest() {
        Long contentId = 1L;
        Long memberId = 10L;

        contentViewHistoryService.registerContentViewHistory(contentId, memberId);
        
        verify(contentViewHistoryRepository, times(1)).registerContentViewHistory(any(ContentViewHistory.class));
    }

    @Test
    @DisplayName("특정 작품 조회 이력 조회 테스트")
    void findContentViewHistoryTest() {
        Long contentId = 1L;

        Pageable pageable = PageRequest.of(0, 10);
        Page<ContentViewResult> mockPage = new PageImpl<>(Collections.emptyList(), pageable, 0);


        when(contentViewHistoryRepository.findContentViewHistories(contentId, pageable)).thenReturn(mockPage);

        Page<ContentViewResult> result = contentViewHistoryService.findContentViewHistory(contentId, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(0);
        verify(contentViewHistoryRepository, times(1)).findContentViewHistories(contentId, pageable);
    }

    @Test
    @DisplayName("사용자의 상위 10개 조회 이력 가져오기 테스트")
    void findTop10ContentViewHistoriesTest() {
        Long memberId = 10L;

        List<TopContentViewResult> mockList = Collections.emptyList();
        when(contentViewHistoryRepository.findTop10ContentViewHistories(memberId)).thenReturn(mockList);

        List<TopContentViewResult> result = contentViewHistoryService.findTop10ContentViewHistories(memberId);

        assertThat(result).isEmpty();
        verify(contentViewHistoryRepository, times(1)).findTop10ContentViewHistories(memberId);
    }

    @Test
    @DisplayName("작품 조회 이력 삭제 테스트")
    void deleteContentViewHistoryTest() {
        Long contentId = 1L;
        contentViewHistoryService.deleteContentViewHistory(contentId);

        verify(contentViewHistoryRepository, times(1)).deleteContentViewHistory(contentId);
    }
}