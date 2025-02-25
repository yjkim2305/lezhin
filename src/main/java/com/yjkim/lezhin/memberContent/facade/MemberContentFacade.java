package com.yjkim.lezhin.memberContent.facade;

import com.yjkim.lezhin.content.application.service.ContentService;
import com.yjkim.lezhin.content.domain.Content;
import com.yjkim.lezhin.memberContent.application.service.MemberContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberContentFacade {
    private final ContentService contentService;
    private final MemberContentService memberContentService;

    public void purchaseContent(Long contentId, Long memberId, boolean isAdult, int episodeNumber) {
        //사용자가 작품 구매 시 성인인증과 에피소드 확인
        Content validateContent = contentService.getValidateContent(contentId, isAdult, episodeNumber);
        //사용자가 작품 구매
        memberContentService.purchaseContent(contentId, memberId, episodeNumber, validateContent.getPriceType());
    }
}
