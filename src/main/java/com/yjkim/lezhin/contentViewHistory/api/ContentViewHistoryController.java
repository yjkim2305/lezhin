package com.yjkim.lezhin.contentViewHistory.api;

import com.yjkim.lezhin.common.api.response.ApiRes;
import com.yjkim.lezhin.contentViewHistory.api.response.ContentViewHistoryResponse;
import com.yjkim.lezhin.contentViewHistory.application.service.ContentViewHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/content-history")
public class ContentViewHistoryController {
    private final ContentViewHistoryService contentViewHistoryService;

    /***
     * 작품별 조회 이력을 가져옵니다
     * @param contentId 조회할 작품의 ID
     * @param pageable 페이지네이션 정보(size, page)
     * @return ContentViewHistoryResponse 객체
     */
    @GetMapping("/{contentId}")
    public ApiRes<ContentViewHistoryResponse> getContentUserHistory(
            @PathVariable("contentId") Long contentId,
            Pageable pageable
    ) {
        return ApiRes.createSuccess(ContentViewHistoryResponse.from(contentViewHistoryService.findContentViewHistory(contentId, pageable)));
    }

}
