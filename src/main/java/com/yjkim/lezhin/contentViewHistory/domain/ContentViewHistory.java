package com.yjkim.lezhin.contentViewHistory.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentViewHistory {
    private Long id;
    private Long contentId;
    private Long memberId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Builder
    private ContentViewHistory(Long id, Long contentId, Long memberId, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.contentId = contentId;
        this.memberId = memberId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public static ContentViewHistory of(Long contentId, Long memberId) {
        return ContentViewHistory.builder()
                .contentId(contentId)
                .memberId(memberId)
                .build();
    }
}
