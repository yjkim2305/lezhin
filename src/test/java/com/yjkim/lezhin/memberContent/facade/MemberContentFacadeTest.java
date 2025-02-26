package com.yjkim.lezhin.memberContent.facade;

import com.yjkim.lezhin.common.exception.CoreException;
import com.yjkim.lezhin.content.api.exception.ContentErrorType;
import com.yjkim.lezhin.content.application.service.ContentService;
import com.yjkim.lezhin.content.domain.Content;
import com.yjkim.lezhin.content.domain.enums.PriceType;
import com.yjkim.lezhin.memberContent.application.service.MemberContentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberContentFacadeTest {

    @Mock
    private ContentService contentService;

    @Mock
    private MemberContentService memberContentService;

    @InjectMocks
    private MemberContentFacade memberContentFacade;

    @Test
    @DisplayName("작품 구매 시, ContentService 및 MemberContentService가 올바르게 호출됨")
    void purchaseContent_Success() {
        Long memberId = 1L;
        Long contentId = 100L;
        int episodeNumber = 5;
        boolean isAdult = false;

        Content content = Content.builder()
                .id(1L)
                .title("테스트 콘텐츠")
                .author("테스트 작가")
                .priceType(PriceType.PAID)
                .totalEpisodes(10)
                .build();

        when(contentService.getValidateContent(contentId, isAdult, episodeNumber)).thenReturn(content);

        memberContentFacade.purchaseContent(contentId, memberId, isAdult, episodeNumber);

        verify(contentService, times(1)).getValidateContent(contentId, isAdult, episodeNumber);
        verify(memberContentService, times(1)).purchaseContent(contentId, memberId, episodeNumber, content.getPriceType());
    }

    @Test
    @DisplayName("에피소드 번호가 존재하지 않는 작품 구매하려 하면 예외가 발생한다.")
    void purchaseContent_Failure_InvalidContent() {
        Long memberId = 1L;
        Long contentId = 100L;
        int episodeNumber = 11;
        boolean isAdult = false;

        when(contentService.getValidateContent(contentId, isAdult, episodeNumber)).thenThrow(new CoreException(ContentErrorType.FORBIDDEN_EPISODE_CONTENT));

        CoreException exception = assertThrows(CoreException.class,
                () ->  memberContentFacade.purchaseContent(contentId, memberId, isAdult, episodeNumber));

        assertThat(ContentErrorType.FORBIDDEN_EPISODE_CONTENT.getMessage()).isEqualTo(exception.getMessage());
        assertThat(ContentErrorType.FORBIDDEN_EPISODE_CONTENT.name()).isEqualTo(exception.getErrorType().getErrorCode());
    }

    @Test
    @DisplayName("성인 인증이 필요한 컨텐츠인데 인증 없이 구매 시 예외가 발생한다.")
    void purchaseContent_Failure_AdultVerificationRequired() {
        Long memberId = 1L;
        Long contentId = 100L;
        int episodeNumber = 5;
        boolean isAdult = false;

        when(contentService.getValidateContent(contentId, isAdult, episodeNumber)).thenThrow(new CoreException(ContentErrorType.FORBIDDEN_ADULT_CONTENT));

        CoreException exception = assertThrows(CoreException.class,
                () ->  memberContentFacade.purchaseContent(contentId, memberId, isAdult, episodeNumber));

        assertThat(ContentErrorType.FORBIDDEN_ADULT_CONTENT.getMessage()).isEqualTo(exception.getMessage());
        assertThat(ContentErrorType.FORBIDDEN_ADULT_CONTENT.name()).isEqualTo(exception.getErrorType().getErrorCode());
    }

}