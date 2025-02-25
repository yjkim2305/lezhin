package com.yjkim.lezhin.memberContent.facade;

import com.yjkim.lezhin.content.application.service.ContentService;
import com.yjkim.lezhin.memberContent.application.service.MemberContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberContentFacade {
    private final ContentService contentService;
    private final MemberContentService memberContentService;

    public void purchaseContent(Long contentId, Long memberId, boolean isAdult, int episodeNumber) {
        contentService.validateContent(contentId, isAdult, episodeNumber);
        //사용자가 구매한 episodeNumber인지 중복체크
        //memberContent에 save


    }
}
