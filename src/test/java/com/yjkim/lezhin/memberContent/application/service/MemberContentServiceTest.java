package com.yjkim.lezhin.memberContent.application.service;

import com.yjkim.lezhin.common.exception.CoreException;
import com.yjkim.lezhin.content.domain.enums.PriceType;
import com.yjkim.lezhin.memberContent.api.exception.MemberContentErrorType;
import com.yjkim.lezhin.memberContent.application.dto.TopMemberContentResult;
import com.yjkim.lezhin.memberContent.application.repository.MemberContentRepository;
import com.yjkim.lezhin.memberContent.domain.MemberContent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class )
class MemberContentServiceTest {
    @Mock
    private MemberContentRepository memberContentRepository;

    @InjectMocks
    private MemberContentService memberContentService;

    @Test
    @DisplayName("중복되지 않은 경우 회원이 컨텐츠를 구매하면 정상적으로 등록된다.")
    void purchaseContent_Success() {
        Long memberId = 1L;
        Long contentId = 100L;
        int episodeNumber = 5;
        PriceType priceType = PriceType.PAID;

        when(memberContentRepository.existMemberContent(contentId, memberId, episodeNumber)).thenReturn(true);

        memberContentService.purchaseContent(contentId, memberId, episodeNumber, priceType);

        verify(memberContentRepository, times(1)).registerMemberContent(any(MemberContent.class));
    }

    @Test
    @DisplayName("이미 구매한 컨텐츠 에피소드는 예외가 발생해야 한다.")
    void purchaseContent_Failure_DuplicateEpisode() {
        Long memberId = 1L;
        Long contentId = 100L;
        int episodeNumber = 5;
        PriceType priceType = PriceType.PAID;

        when(memberContentRepository.existMemberContent(contentId, memberId, episodeNumber)).thenReturn(false);

        CoreException exception = assertThrows(CoreException.class,
                () -> memberContentService.purchaseContent(contentId, memberId, episodeNumber, priceType));

        assertThat(MemberContentErrorType.EXIST_CONTENT_EPISODE.getMessage()).isEqualTo(exception.getMessage());
        assertThat(MemberContentErrorType.EXIST_CONTENT_EPISODE.name()).isEqualTo(exception.getErrorType().getErrorCode());
    }

    @Test
    @DisplayName("회원이 가장 많이 본 컨텐츠 상위 10개를 조회한다.")
    void findTop10MemberContent_Success() {
        Long memberId = 1L;
        List<TopMemberContentResult> mockResults = List.of(
                new TopMemberContentResult("작품1", "작가1", 15L),
                new TopMemberContentResult("작품2", "작가2", 10L)
        );
        when(memberContentRepository.findTop10MemberContent(memberId)).thenReturn(mockResults);

        List<TopMemberContentResult> result = memberContentService.findTop10MemberContent(memberId);

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).title()).isEqualTo("작품1");
        assertThat(result.get(0).contentEpisodeCount()).isEqualTo(15);
    }
}