package com.yjkim.lezhin.member.domain;

import com.yjkim.lezhin.member.application.dto.MemberCreateCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

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

}