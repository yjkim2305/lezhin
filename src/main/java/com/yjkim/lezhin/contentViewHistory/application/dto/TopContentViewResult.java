package com.yjkim.lezhin.contentViewHistory.application.dto;

public record TopContentViewResult(
        String title,
        String author,
        Long viewCount
) {
}
