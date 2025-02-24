package com.yjkim.lezhin.content.api.response;

import com.yjkim.lezhin.content.application.dto.ContentResultDto;
import com.yjkim.lezhin.content.domain.enums.ContentType;
import com.yjkim.lezhin.content.domain.enums.PriceType;

public record ContentDetailResponse(
        String title,
        String author,
        ContentType contentType,
        PriceType priceType,
        int totalEpisodes
) {
    public static ContentDetailResponse from(ContentResultDto contentResultDto) {
        return new ContentDetailResponse(contentResultDto.title(), contentResultDto.author(), contentResultDto.contentType(), contentResultDto.priceType(), contentResultDto.totalEpisodes());
    }
}
