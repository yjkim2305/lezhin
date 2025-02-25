package com.yjkim.lezhin.memberContent.api.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record MemberContentCreateRequest(
        @NotBlank(message = "작품의 ID는 필수 값 입니다.")
        Long contentId,

        @Min(value = 1, message = "작품의 에피소드 수는 최소 1 이상이어야 합니다.")
        int episodeNumber
) {
}
