package com.yjkim.lezhin.contentViewHistory.api.response;

import com.yjkim.lezhin.contentViewHistory.application.dto.TopContentViewResult;

import java.util.List;

public record TopContentViewHistoryResponse(
        List<TopContentViewResult> topContents
) {
    public static TopContentViewHistoryResponse from(List<TopContentViewResult> topContentViewResults) {
        return new TopContentViewHistoryResponse(topContentViewResults);
    }
}
