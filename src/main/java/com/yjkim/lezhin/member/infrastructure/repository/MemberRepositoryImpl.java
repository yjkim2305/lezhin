package com.yjkim.lezhin.member.infrastructure.repository;

import com.yjkim.lezhin.member.application.repository.MemberRepository;
import com.yjkim.lezhin.member.domain.Member;
import com.yjkim.lezhin.member.infrastructure.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberJpaRespository memberJpaRespository;

    @Override
    public Boolean existsByMemberEmail(String memberEmail) {
        return memberJpaRespository.existsByMemberEmail(memberEmail);
    }

    @Override
    public void signUpMember(Member member) {
        memberJpaRespository.save(MemberEntity.toEntity(member));
    }
}
