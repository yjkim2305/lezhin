package com.yjkim.lezhin.member.application.service;

import com.yjkim.lezhin.common.util.JwtUtil;
import com.yjkim.lezhin.member.domain.Member;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
                .build();

        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        when(jwtUtil.createAccessJwt("access", member.getId().toString())).thenReturn(accessToken);
        when(jwtUtil.createRefreshJwt("refresh", member.getId().toString())).thenReturn(refreshToken);

        authService.generateTokens(member);

        verify(jwtUtil, times(1)).createAccessJwt("access", member.getId().toString());
        verify(jwtUtil, times(1)).createRefreshJwt("refresh", member.getId().toString());
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