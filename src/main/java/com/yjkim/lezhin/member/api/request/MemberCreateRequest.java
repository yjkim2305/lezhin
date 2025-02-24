package com.yjkim.lezhin.member.api.request;

import jakarta.validation.constraints.NotBlank;

public record MemberCreateRequest(
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        String memberEmail,
        @NotBlank(message = "비밀번호 필수 입력 값입니다.")
        String password,
        String memberName,
        @NotBlank(message = "생년월인은 필수 입력 값이며, YYYYMMDD 형식이어야 합니다.")
        String birthDate
) {
}
