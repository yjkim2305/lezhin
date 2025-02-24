package com.yjkim.lezhin.member.application.dto;

import com.yjkim.lezhin.member.api.request.MemberLoginRequest;

public record MemberLoginCommand(
        String memberEmail,
        String password
) {
    public static MemberLoginCommand from(MemberLoginRequest rq) {
        return new MemberLoginCommand(rq.memberEmail(), rq.password());
    }
}
