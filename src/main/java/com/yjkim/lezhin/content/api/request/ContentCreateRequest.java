package com.yjkim.lezhin.content.api.request;

import com.yjkim.lezhin.content.domain.enums.ContentType;
import com.yjkim.lezhin.content.domain.enums.PriceType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContentCreateRequest(
        @NotBlank(message = "제목은 필수 입력 값입니다.")
        String title,

        @NotBlank(message = "작가명은 필수 입력 값입니다.")
        String author,

        @NotNull(message = "콘텐츠 타입은 필수 입력 값입니다. 값은 GENERAL, ADULT 입니다.")
        ContentType contentType,

        @NotNull(message = "가격 타입은 필수 입력 값입니다. 값은 FREE, PAID 입니다.")
        PriceType priceType,

        @Min(value = 1, message = "총 에피소드 수는 최소 1 이상이어야 합니다.")
        int totalEpisodes
) {
}
