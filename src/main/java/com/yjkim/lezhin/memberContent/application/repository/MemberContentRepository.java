package com.yjkim.lezhin.memberContent.application.repository;

import com.yjkim.lezhin.memberContent.application.dto.TopMemberContentResult;
import com.yjkim.lezhin.memberContent.domain.MemberContent;

import java.util.List;

public interface MemberContentRepository {
    boolean existMemberContent(Long memberId, Long contentId, int episodeNumber);
    void registerMemberContent(MemberContent memberContent);
    List<TopMemberContentResult> findTop10MemberContent(Long memberId);
}
