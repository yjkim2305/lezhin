package com.yjkim.lezhin.member.application.repository;

import com.yjkim.lezhin.member.domain.Member;

public interface MemberRepository {
    Boolean existsByMemberEmail(String memberEmail);
    void signUpMember(Member member);
}
