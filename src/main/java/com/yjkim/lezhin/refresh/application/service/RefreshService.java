package com.yjkim.lezhin.refresh.application.service;

import com.yjkim.lezhin.common.exception.CoreException;
import com.yjkim.lezhin.common.util.JwtUtil;
import com.yjkim.lezhin.refresh.api.exception.RefreshErrorType;
import com.yjkim.lezhin.refresh.application.dto.RefreshReissueDto;
import com.yjkim.lezhin.refresh.application.repository.RefreshRepository;
import com.yjkim.lezhin.refresh.domain.Refresh;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshService {
    @Value("${spring.jwt.refresh.expiration}")
    private Long refreshExpiration;

    private final RefreshRepository refreshRepository;
    private final JwtUtil jwtUtil;

    public void addRefreshToken(String memberId, String refreshToken, Long expiredMs) {
        refreshRepository.save(Refresh.of(memberId, refreshToken, expiredMs));
    }

    @Transactional
    public RefreshReissueDto reissueToken(HttpServletRequest request) {
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refreshToken = cookie.getValue();
            }
        }

        if (refreshToken == null) {
            throw new CoreException(RefreshErrorType.NOT_EXIST_AUTHORIZATION);
        }

        //만료 여부와 카테고리가 맞는지 확인
        jwtUtil.validateAccessToken(refreshToken, "refresh");

        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefreshToken(refreshToken);
        if (!isExist) {
            throw new CoreException(RefreshErrorType.INVALID_REFRESH_TOKEN);
        }

        String memberId = jwtUtil.getMemberId(refreshToken);
        boolean isAdult = jwtUtil.getIsAdult(refreshToken);

        //make new JWT
        String newAccess = jwtUtil.createAccessJwt("access", memberId, isAdult);
        String newRefresh = jwtUtil.createRefreshJwt("refresh", memberId, isAdult);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefreshToken(refreshToken);
        refreshRepository.save(Refresh.of(memberId, newRefresh, refreshExpiration));

        return RefreshReissueDto.of(newAccess, newRefresh);
    }

    public Cookie createRefreshCookie(String refreshToken) {
        return jwtUtil.createCookie("refresh", refreshToken);
    }
}
