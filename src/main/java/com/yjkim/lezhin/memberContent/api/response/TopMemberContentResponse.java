package com.yjkim.lezhin.memberContent.api.response;

import com.yjkim.lezhin.memberContent.application.dto.TopMemberContentResult;

import java.util.List;

public record TopMemberContentResponse(
        List<TopMemberContentResult> topMemberContents
) {
    public static TopMemberContentResponse from(List<TopMemberContentResult> topMemberContentResult) {
        return new TopMemberContentResponse(topMemberContentResult);
    }
}
