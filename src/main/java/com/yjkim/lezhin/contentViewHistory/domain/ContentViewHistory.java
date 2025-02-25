package com.yjkim.lezhin.contentViewHistory.domain;

import lombok.AccessLevel;
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
}
