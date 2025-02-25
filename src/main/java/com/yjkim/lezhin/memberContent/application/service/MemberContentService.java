package com.yjkim.lezhin.memberContent.application.service;

import com.yjkim.lezhin.memberContent.application.repository.MemberContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberContentService {
    private final MemberContentRepository memberContentRepository;

    public void purchaseContent(Long contentId, Long memberId, int episodeNumber) {

    }

}
