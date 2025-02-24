package com.yjkim.lezhin.content.infrastructure.entity;

import com.yjkim.lezhin.common.domain.entity.BaseTimeEntity;
import com.yjkim.lezhin.content.domain.Content;
import com.yjkim.lezhin.content.domain.enums.ContentType;
import com.yjkim.lezhin.content.domain.enums.PriceType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "content")
public class ContentEntity extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @Enumerated(EnumType.STRING)
    private PriceType priceType;

    private int totalEpisodes;

    @Builder
    private ContentEntity(Long id, String title, String author, ContentType contentType, PriceType priceType, int totalEpisodes) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.contentType = contentType;
        this.priceType = priceType;
        this.totalEpisodes = totalEpisodes;
    }

    public static ContentEntity toEntity(Content content) {
        return ContentEntity.builder()
                .id(content.getId())
                .title(content.getTitle())
                .author(content.getAuthor())
                .contentType(content.getContentType())
                .priceType(content.getPriceType())
                .totalEpisodes(content.getTotalEpisodes())
                .build();
    }

}
