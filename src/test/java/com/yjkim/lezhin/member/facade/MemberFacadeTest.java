package com.yjkim.lezhin.member.facade;

import com.yjkim.lezhin.common.exception.CoreException;
import com.yjkim.lezhin.member.api.exception.MemberErrorType;
import com.yjkim.lezhin.member.application.dto.MemberLoginCommand;
import com.yjkim.lezhin.member.application.dto.MemberLoginDto;
import com.yjkim.lezhin.member.application.service.AuthService;
import com.yjkim.lezhin.member.application.service.MemberService;
import com.yjkim.lezhin.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberFacadeTest {

    @Mock
    private MemberService memberService;

    @Mock
    private AuthService authService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private MemberFacade memberFacade;

    @Test
    @DisplayName("로그인 성공 테스트")
    void login_Success() {
        MemberLoginDto memberLoginDto = new MemberLoginDto("accessToken", "refreshToken");
        MemberLoginCommand memberLoginCommand = new MemberLoginCommand("test@kidaristudio.com", "password");
        Member member = Member.builder()
                .id(1L)
                .memberEmail("test@kidaristudio.com")
                .memberName("test")
                .password("password")
                .build();

        when(memberService.findByMemberEmail(memberLoginCommand.memberEmail())).thenReturn(member);
        when(bCryptPasswordEncoder.matches(memberLoginCommand.password(), member.getPassword())).thenReturn(true);
        when(authService.generateTokens(member)).thenReturn(memberLoginDto);

        MemberLoginDto result = memberFacade.login(memberLoginCommand);

        assertEquals(memberLoginDto.accessToken(), result.accessToken());
        assertEquals(memberLoginDto.refreshToken(), result.refreshToken());
    }

    @Test
    @DisplayName("비밀번호가 다를 경우 로그인에 실패")
    void login_Fail() {
        MemberLoginDto memberLoginDto = new MemberLoginDto("accessToken", "refreshToken");
        MemberLoginCommand memberLoginCommand = new MemberLoginCommand("test@kidaristudio.com", "password123");
        Member member = Member.builder()
                .id(1L)
                .memberEmail("test@kidaristudio.com")
                .memberName("test")
                .password("password")
                .build();

        when(memberService.findByMemberEmail(memberLoginCommand.memberEmail())).thenReturn(member);
        when(bCryptPasswordEncoder.matches(memberLoginCommand.password(), member.getPassword())).thenReturn(false);

        CoreException exception = assertThrows(CoreException.class, () -> {
            memberFacade.login(memberLoginCommand);
        });

        assertEquals(MemberErrorType.NOT_EXIST_USER_PASSWORD.getMessage(), exception.getMessage());
        assertEquals(MemberErrorType.NOT_EXIST_USER_PASSWORD.name(), exception.getErrorType().getErrorCode());
    }

}