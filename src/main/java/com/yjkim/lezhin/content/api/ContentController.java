package com.yjkim.lezhin.content.api;

import com.yjkim.lezhin.common.api.response.ApiRes;
import com.yjkim.lezhin.common.util.JwtUtil;
import com.yjkim.lezhin.content.api.request.ContentCreateRequest;
import com.yjkim.lezhin.content.api.response.ContentDetailResponse;
import com.yjkim.lezhin.content.application.dto.ContentCreateCommand;
import com.yjkim.lezhin.content.application.service.ContentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/content")
public class ContentController {
    private final ContentService contentService;
    private final JwtUtil jwtUtil;

    /***
     * 새로운 작품을 등록합니다.
     * @param rq 작품 등록 요청 객체
     *           - title(String): 작품 제목   (필수)
     *           - author(String): 작가명     (필수)
     *           - contentType (ContentType): 콘텐츠 유형 (필수)
     *           - priceType (PriceType): 가격 유형 (필수)
     *           - totalEpisodes (int): 총 에피소드 수 (1 이상 필수)
     * @return 성공적으로 등록되었을 경우 응답 객체
     *           - 성공: HTTP 200 OK
     *           - 유효성 검증 실패: HTTP 400 BAD REQUEST
     */
    @PostMapping
    public ApiRes<?> registerContent(@Valid @RequestBody ContentCreateRequest rq) {
        contentService.registerContent(ContentCreateCommand.from(rq));
        return ApiRes.createSuccessWithNoContent();
    }


    @GetMapping("/{contentId}")
    public ApiRes<ContentDetailResponse> getContent(@PathVariable(value = "contentId") Long contentId, HttpServletRequest request) {
        String token = jwtUtil.getJwtFromRequest(request);
        String memberId = jwtUtil.getMemberId(token);
        boolean isAdult = jwtUtil.getIsAdult(token);
        return ApiRes.createSuccess(ContentDetailResponse.from(contentService.getContent(contentId, Long.parseLong(memberId), isAdult)));
    }


}
