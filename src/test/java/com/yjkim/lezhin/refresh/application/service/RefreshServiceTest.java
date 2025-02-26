package com.yjkim.lezhin.refresh.application.service;

import com.yjkim.lezhin.common.util.JwtUtil;
import com.yjkim.lezhin.refresh.application.dto.RefreshReissueDto;
import com.yjkim.lezhin.refresh.application.repository.RefreshRepository;
import com.yjkim.lezhin.refresh.domain.Refresh;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshServiceTest {

    @Mock
    private RefreshRepository refreshRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private RefreshService refreshService;

    private final Long refreshExpiration = 604800L;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(refreshService, "refreshExpiration", refreshExpiration);
    }

    @Test
    @DisplayName("refresh 토큰이 정상적으로 저장된다.")
    void addRefreshToken_Success() {
        String memberId = "1";
        String refreshToken = "refreshToken";
        Refresh mockRefresh = mock(Refresh.class);

        try (var mockedStatic = mockStatic(Refresh.class)) {
            when(Refresh.of(memberId, refreshToken, refreshExpiration)).thenReturn(mockRefresh);
            refreshService.addRefreshToken(memberId, refreshToken, refreshExpiration);
            verify(refreshRepository, times(1)).save(mockRefresh);
        }
    }

    @Test
    @DisplayName("refresh 토큰으로 access 토큰이 정상적으로 발급된다.")
    void reissueToken_Success() {
        String memberId = "1";
        boolean isAdult = true;
        String newAccessToken = "newAccessToken";
        String newRefreshToken = "newRefreshToken";
        String refreshToken = "refreshToken";

        HttpServletRequest request = mock(HttpServletRequest.class);
        Cookie refreshCookie = new Cookie("refresh", refreshToken);
        when(request.getCookies()).thenReturn(new Cookie[]{refreshCookie});

        doNothing().when(jwtUtil).validateAccessToken(refreshToken, "refresh");
        when(refreshRepository.existsByRefreshToken(refreshToken)).thenReturn(true);
        when(jwtUtil.getMemberId(refreshToken)).thenReturn(memberId);
        when(jwtUtil.getIsAdult(refreshToken)).thenReturn(isAdult);
        when(jwtUtil.createAccessJwt("access", memberId, isAdult)).thenReturn(newAccessToken);
        when(jwtUtil.createRefreshJwt("refresh", memberId, isAdult)).thenReturn(newRefreshToken);

        try (var mockedStatic = mockStatic(Refresh.class)) {
            when(Refresh.of(memberId, newRefreshToken, refreshExpiration)).thenReturn(mock(Refresh.class));

            RefreshReissueDto result = refreshService.reissueToken(request);

            assertThat(result).isNotNull();
            assertThat(result.accessToken()).isEqualTo(newAccessToken);
            assertThat(result.refreshToken()).isEqualTo(newRefreshToken);

            verify(refreshRepository, times(1)).deleteByRefreshToken(refreshToken);
            verify(refreshRepository, times(1)).save(any(Refresh.class));
        }
    }
}