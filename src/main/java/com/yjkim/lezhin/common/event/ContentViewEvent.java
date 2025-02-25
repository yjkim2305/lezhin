package com.yjkim.lezhin.common.event;

public record ContentViewEvent(
        Long contentId,
        Long memberId
) {
}
