package com.yjkim.lezhin.member.application.dto;

public record MemberCreateCommand(
        String memberEmail,
        String password,
        String memberName,
        String birthDate    //yyyyMMdd 형식의 생년월일
) {
}
