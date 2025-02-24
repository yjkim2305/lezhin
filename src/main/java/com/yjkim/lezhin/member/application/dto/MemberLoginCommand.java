package com.yjkim.lezhin.member.application.dto;

public record MemberLoginCommand(
        String memberEmail,
        String password
) {
}
