package com.yjkim.lezhin.content.domain;

import com.yjkim.lezhin.content.domain.enums.ContentType;
import com.yjkim.lezhin.content.domain.enums.PriceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {
    private Long id;
    private String title;
    private String author;
    private ContentType contentType;
    private PriceType priceType;
    private int totalEpisodes;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
