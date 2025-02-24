package com.yjkim.lezhin.member.api.response;

import com.yjkim.lezhin.member.application.dto.MemberLoginDto;

public record MemberLoginResponse(
        String accessToken,
        String refreshToken
) {
    public static MemberLoginResponse from(MemberLoginDto memberLoginDto) {
        return new MemberLoginResponse(memberLoginDto.accessToken(), memberLoginDto.refreshToken());
    }
}
