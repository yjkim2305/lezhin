package com.yjkim.lezhin.member.application.service;

import com.yjkim.lezhin.common.util.JwtUtil;
import com.yjkim.lezhin.member.application.dto.MemberLoginDto;
import com.yjkim.lezhin.member.domain.Member;
import com.yjkim.lezhin.refresh.application.service.RefreshService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final RefreshService refreshService;

    @Value("${spring.jwt.refresh.expiration}")
    private Long refreshExpiration;

    public MemberLoginDto generateTokens(Member member) {
        String accessToken = jwtUtil.createAccessJwt("access", member.getId().toString(), member.isAdult());
        String refreshToken = jwtUtil.createRefreshJwt("refresh", member.getId().toString(), member.isAdult());

        //refresh token DB 적재
        refreshService.addRefreshToken(member.getId().toString(), refreshToken, refreshExpiration);

        return MemberLoginDto.of(accessToken, refreshToken);
    }

    public Cookie createRefreshCookie(String refreshToken) {
        return jwtUtil.createCookie("refresh", refreshToken);
    }

}
