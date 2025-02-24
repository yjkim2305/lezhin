package com.yjkim.lezhin.content.application.dto;

import com.yjkim.lezhin.content.api.request.ContentCreateRequest;
import com.yjkim.lezhin.content.domain.enums.ContentType;
import com.yjkim.lezhin.content.domain.enums.PriceType;

public record ContentCreateCommand(
        String title,
        String author,
        ContentType contentType,
        PriceType priceType,
        int totalEpisodes
) {
    public static ContentCreateCommand from(ContentCreateRequest rq) {
        return new ContentCreateCommand(rq.title(), rq.author(), rq.contentType(), rq.priceType(), rq.totalEpisodes());
    }
}
