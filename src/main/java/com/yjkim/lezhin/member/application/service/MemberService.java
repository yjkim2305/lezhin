package com.yjkim.lezhin.member.application.service;

import com.yjkim.lezhin.common.exception.CoreException;
import com.yjkim.lezhin.member.api.exception.MemberErrorType;
import com.yjkim.lezhin.member.application.dto.MemberCreateCommand;
import com.yjkim.lezhin.member.application.repository.MemberRepository;
import com.yjkim.lezhin.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Member findByMemberEmail(String memberEmail) {
        return memberRepository.findByMemberEmail(memberEmail);
    }

    public void signUpMember(MemberCreateCommand memberCreateCommand) {

        if (memberRepository.existsByMemberEmail(memberCreateCommand.memberEmail())) {
            throw new CoreException(MemberErrorType.EXIST_USER);
        }

        Member member = Member.createWithEncodedPassword(memberCreateCommand, bCryptPasswordEncoder);
        memberRepository.signUpMember(member);
    }
}
