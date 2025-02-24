package com.yjkim.lezhin.member.application.dto;

public record MemberLoginDto(
        String accessToken,
        String refreshToken
) {
    public static MemberLoginDto of(String accessToken, String refreshToken) {
        return new MemberLoginDto(accessToken, refreshToken);
    }
}
