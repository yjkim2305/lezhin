package com.yjkim.lezhin.member.facade;

import com.yjkim.lezhin.member.application.dto.MemberLoginCommand;
import com.yjkim.lezhin.member.application.dto.MemberLoginDto;
import com.yjkim.lezhin.member.application.service.AuthService;
import com.yjkim.lezhin.member.application.service.MemberService;
import com.yjkim.lezhin.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberService memberService;
    private final AuthService authService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberLoginDto login(MemberLoginCommand memberLoginCommand) {
        Member member = memberService.findByMemberEmail(memberLoginCommand.memberEmail());

        //비밀번호 검증
        member.validatePassword(memberLoginCommand.password(), bCryptPasswordEncoder);

        //jwt 토큰 생성
        return authService.generateTokens(member);
    }

}
