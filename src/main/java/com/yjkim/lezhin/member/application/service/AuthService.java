package com.yjkim.lezhin.member.application.service;

import com.yjkim.lezhin.common.util.JwtUtil;
import com.yjkim.lezhin.member.application.dto.MemberLoginDto;
import com.yjkim.lezhin.member.domain.Member;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;

    public MemberLoginDto generateTokens(Member member) {
        String accessToken = jwtUtil.createAccessJwt("access", member.getId().toString());
        String refreshToken = jwtUtil.createRefreshJwt("refresh", member.getId().toString());

        //refresh token DB 적재 필요

        return MemberLoginDto.of(accessToken, refreshToken);
    }

    public Cookie createRefreshCookie(String refreshToken) {
        return jwtUtil.createCookie("refresh", refreshToken);
    }

}
