package com.yjkim.lezhin.content.domain;

import com.yjkim.lezhin.content.application.dto.ContentCreateCommand;
import com.yjkim.lezhin.content.domain.enums.ContentType;
import com.yjkim.lezhin.content.domain.enums.PriceType;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Builder
    private Content(Long id, String title, String author, ContentType contentType, PriceType priceType, int totalEpisodes, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.contentType = contentType;
        this.priceType = priceType;
        this.totalEpisodes = totalEpisodes;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public static Content from(ContentCreateCommand contentCreateCommand) {
        return Content.builder()
                .title(contentCreateCommand.title())
                .author(contentCreateCommand.author())
                .contentType(contentCreateCommand.contentType())
                .priceType(contentCreateCommand.priceType())
                .totalEpisodes(contentCreateCommand.totalEpisodes())
                .build();
    }
}
