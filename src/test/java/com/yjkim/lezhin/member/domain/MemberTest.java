package com.yjkim.lezhin.member.domain;

import com.yjkim.lezhin.common.exception.CoreException;
import com.yjkim.lezhin.member.api.exception.MemberErrorType;
import com.yjkim.lezhin.member.application.dto.MemberCreateCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberTest {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    @DisplayName("비밀번호가 암호화된 상태로 Member 객체가 정상적으로 생성되어야 한다.")
    void createWithEncodedPassword() {
        MemberCreateCommand memberCreateCommand = new MemberCreateCommand("test@kidaristudio.com", "securePassword", "테스터", "19910828");

        Member member = Member.createWithEncodedPassword(memberCreateCommand, bCryptPasswordEncoder);

        assertThat(member.getMemberEmail()).isEqualTo("test@kidaristudio.com");
        assertThat(member.getMemberName()).isEqualTo("테스터");
        assertThat(member.getBirthDate()).isEqualTo("1991-08-28");
        assertThat(bCryptPasswordEncoder.matches("securePassword", member.getPassword())).isTrue();
    }

    @Test
    @DisplayName("비밀번호가 올바르면 검증을 통과한다.")
    void validatePassword_Success() {
        MemberCreateCommand memberCreateCommand = new MemberCreateCommand("test@kidaristudio.com", "securePassword", "테스터", "19910828");
        Member member = Member.createWithEncodedPassword(memberCreateCommand, bCryptPasswordEncoder);

        assertDoesNotThrow(() -> member.validatePassword("securePassword", bCryptPasswordEncoder));
    }

    @Test
    @DisplayName("비밀번호가 올바르지 않으면 검증에서 예외가 발생한다.")
    void validatePassword_Fail() {
        Member member = Member.builder()
                .memberEmail("test@kidaristudio.com")
                .memberName("test")
                .password("password")
                .build();

        CoreException exception = assertThrows(CoreException.class, ()
                -> member.validatePassword("password", bCryptPasswordEncoder)
        );

        assertThat(MemberErrorType.NOT_EXIST_USER_PASSWORD.getMessage()).isEqualTo(exception.getMessage());
        assertThat(MemberErrorType.NOT_EXIST_USER_PASSWORD.name()).isEqualTo(exception.getErrorType().getErrorCode());

    }

}