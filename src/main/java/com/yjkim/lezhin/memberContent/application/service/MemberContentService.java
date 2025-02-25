package com.yjkim.lezhin.memberContent.application.service;

import com.yjkim.lezhin.common.exception.CoreException;
import com.yjkim.lezhin.content.domain.enums.PriceType;
import com.yjkim.lezhin.memberContent.api.exception.MemberContentErrorType;
import com.yjkim.lezhin.memberContent.application.repository.MemberContentRepository;
import com.yjkim.lezhin.memberContent.domain.MemberContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberContentService {
    private final MemberContentRepository memberContentRepository;

    public void purchaseContent(Long contentId, Long memberId, int episodeNumber, PriceType priceType) {
        //사용자가 구매한 episodeNumber인지 중복체크
        if (!memberContentRepository.existMemberContent(contentId, memberId, episodeNumber)) {
            throw new CoreException(MemberContentErrorType.EXIST_CONTENT_EPISODE);
        }

        //사용자 작품 구매
        memberContentRepository.registerMemberContent(MemberContent.of(memberId, contentId, episodeNumber, priceType));
    }

}
