package com.yjkim.lezhin.memberContent.application.repository;

import com.yjkim.lezhin.memberContent.domain.MemberContent;

public interface MemberContentRepository {
    boolean existMemberContent(Long memberId, Long contentId, int episodeNumber);
    void registerMemberContent(MemberContent memberContent);
}
