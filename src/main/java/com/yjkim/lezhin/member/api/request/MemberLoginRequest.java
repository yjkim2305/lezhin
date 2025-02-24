package com.yjkim.lezhin.member.api.request;

import jakarta.validation.constraints.NotBlank;

public record MemberLoginRequest(
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        String memberEmail,
        @NotBlank(message = "비밀번호 필수 입력 값입니다.")
        String password
) {
}
