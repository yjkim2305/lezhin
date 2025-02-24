package com.yjkim.lezhin.member.application.service;

import com.yjkim.lezhin.common.util.JwtUtil;
import com.yjkim.lezhin.member.domain.Member;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    void generateTokens_Success() {
        Member member = Member.builder()
                .id(1L)
                .memberEmail("test@kidaristudio.com")
                .memberName("test")
                .password("password")
                .birthDate(LocalDate.of(1991,8,28))
                .build();

        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        when(jwtUtil.createAccessJwt("access", member.getId().toString(), member.isAdult())).thenReturn(accessToken);
        when(jwtUtil.createRefreshJwt("refresh", member.getId().toString(), member.isAdult())).thenReturn(refreshToken);

        authService.generateTokens(member);

        verify(jwtUtil, times(1)).createAccessJwt("access", member.getId().toString(), member.isAdult());
        verify(jwtUtil, times(1)).createRefreshJwt("refresh", member.getId().toString(), member.isAdult());
    }

    @Test
    void createRefreshCookie_Success() {
        String refreshToken = "refreshToken";
        Cookie expectedCookie = new Cookie("refresh", refreshToken);
        when(jwtUtil.createCookie("refresh", refreshToken)).thenReturn(expectedCookie);

        Cookie result = authService.createRefreshCookie(refreshToken);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("refresh");
        assertThat(result.getValue()).isEqualTo(refreshToken);
        verify(jwtUtil, times(1)).createCookie("refresh", refreshToken);
    }


}