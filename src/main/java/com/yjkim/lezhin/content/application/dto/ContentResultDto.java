package com.yjkim.lezhin.content.application.dto;

import com.yjkim.lezhin.content.domain.Content;
import com.yjkim.lezhin.content.domain.enums.ContentType;
import com.yjkim.lezhin.content.domain.enums.PriceType;

public record ContentResultDto(
        String title,
        String author,
        ContentType contentType,
        PriceType priceType,
        int totalEpisodes
) {
    public static ContentResultDto from(Content content) {
        return new ContentResultDto(content.getTitle(), content.getAuthor(), content.getContentType(), content.getPriceType(), content.getTotalEpisodes());
    }
}
