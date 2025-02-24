package com.yjkim.lezhin.member.application.service;

import com.yjkim.lezhin.common.exception.CoreException;
import com.yjkim.lezhin.member.api.exception.MemberErrorType;
import com.yjkim.lezhin.member.application.dto.MemberCreateCommand;
import com.yjkim.lezhin.member.application.repository.MemberRepository;
import com.yjkim.lezhin.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 처음하는 사용자는 회원가입에 성공해야 한다.")
    void signUpMember_Success() {
        MemberCreateCommand memberCreateCommand = new MemberCreateCommand("test@kidaristudio.com", "securePassword", "테스터", "19910828");

        when(memberRepository.existsByMemberEmail(memberCreateCommand.memberEmail())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(memberCreateCommand.password())).thenReturn("encodedPassword");

        memberService.signUpMember(memberCreateCommand);

        verify(memberRepository, times(1)).signUpMember(any(Member.class));
    }

    @Test
    @DisplayName("이미 존재하는 사용자는 회원가입 할 경우 예외가 발생해야 한다.")
    void signUpMember_Fail() {
        MemberCreateCommand memberCreateCommand = new MemberCreateCommand("test@kidaristudio.com", "securePassword", "테스터", "19910828");

        when(memberRepository.existsByMemberEmail(memberCreateCommand.memberEmail())).thenReturn(true);

        CoreException exception = assertThrows(CoreException.class, () -> {
            memberService.signUpMember(memberCreateCommand);
        });

        assertThat(MemberErrorType.EXIST_USER.getMessage()).isEqualTo(exception.getMessage());
        assertThat(MemberErrorType.EXIST_USER.name()).isEqualTo(exception.getErrorType().getErrorCode());
    }

}