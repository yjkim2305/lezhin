package com.yjkim.lezhin.contentViewHistory.api.response;

import com.yjkim.lezhin.contentViewHistory.application.dto.ContentViewResult;
import org.springframework.data.domain.Page;

import java.util.List;

public record ContentViewHistoryResponse(
        List<ContentViewResult> content,
        int pageNumber,
        int totalPages,
        long totalElements
) {
    public static ContentViewHistoryResponse from(Page<ContentViewResult> page) {
        return new ContentViewHistoryResponse(page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements());
    }
}
