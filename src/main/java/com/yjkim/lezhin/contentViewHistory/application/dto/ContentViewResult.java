package com.yjkim.lezhin.contentViewHistory.application.dto;

import java.time.LocalDateTime;

public record ContentViewResult(
        String memberName,
        String memberEmail,
        LocalDateTime viewDate
) {
}
