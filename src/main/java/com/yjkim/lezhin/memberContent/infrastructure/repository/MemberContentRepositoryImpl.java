package com.yjkim.lezhin.memberContent.infrastructure.repository;

import com.yjkim.lezhin.memberContent.application.dto.TopMemberContentResult;
import com.yjkim.lezhin.memberContent.application.repository.MemberContentRepository;
import com.yjkim.lezhin.memberContent.domain.MemberContent;
import com.yjkim.lezhin.memberContent.infrastructure.entity.MemberContentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberContentRepositoryImpl implements MemberContentRepository {
    private final MemberContentJpaRepository memberContentJpaRepository;


    @Override
    public boolean existMemberContent(Long memberId, Long contentId, int episodeNumber) {
        return memberContentJpaRepository.findContentWithLock(memberId, contentId, episodeNumber).isEmpty();
    }

    @Override
    public void registerMemberContent(MemberContent memberContent) {
        memberContentJpaRepository.save(MemberContentEntity.toEntity(memberContent));
    }

    @Override
    public List<TopMemberContentResult> findTop10MemberContent(Long memberId) {
        return memberContentJpaRepository.findTop10MemberContent(memberId);
    }
}
