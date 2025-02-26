package com.yjkim.lezhin.memberContent.application.dto;

public record TopMemberContentResult(
        String title,
        String author,
        Long contentEpisodeCount
) {
}
