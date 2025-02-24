package com.yjkim.lezhin.member.application.dto;

import com.yjkim.lezhin.member.api.request.MemberCreateRequest;

public record MemberCreateCommand(
        String memberEmail,
        String password,
        String memberName,
        String birthDate    //yyyyMMdd 형식의 생년월일
) {

    public static MemberCreateCommand from(MemberCreateRequest rq) {
        return new MemberCreateCommand(rq.memberEmail(), rq.password(), rq.memberName(), rq.birthDate());
    }
}
