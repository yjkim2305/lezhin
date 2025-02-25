package com.yjkim.lezhin.memberContent.infrastructure.repository;

import com.yjkim.lezhin.memberContent.application.repository.MemberContentRepository;
import com.yjkim.lezhin.memberContent.domain.MemberContent;
import com.yjkim.lezhin.memberContent.infrastructure.entity.MemberContentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
