package com.yjkim.lezhin.memberContent.infrastructure.repository;

import com.yjkim.lezhin.memberContent.application.dto.TopMemberContentResult;

import java.util.List;

public interface CustomMemberContentJpaRepository {
    List<TopMemberContentResult> findTop10MemberContent(Long memberId);
}
