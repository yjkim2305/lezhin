package com.yjkim.lezhin.member.application.service;

import com.yjkim.lezhin.common.util.JwtUtil;
import com.yjkim.lezhin.member.domain.Member;
import com.yjkim.lezhin.refresh.application.service.RefreshService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RefreshService refreshService;

    @InjectMocks
    private AuthService authService;

    private final Long refreshExpiration = 604800L;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(authService, "refreshExpiration", refreshExpiration);
    }

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
        doNothing().when(refreshService).addRefreshToken(member.getId().toString(), refreshToken, refreshExpiration);

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