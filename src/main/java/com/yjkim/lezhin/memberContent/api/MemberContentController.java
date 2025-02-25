package com.yjkim.lezhin.memberContent.api;

import com.yjkim.lezhin.common.api.response.ApiRes;
import com.yjkim.lezhin.common.util.JwtUtil;
import com.yjkim.lezhin.memberContent.api.request.MemberContentCreateRequest;
import com.yjkim.lezhin.memberContent.facade.MemberContentFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member-content")
public class MemberContentController {
    private final MemberContentFacade memberContentFacade;
    private final JwtUtil jwtUtil;

    /***
     * 특정 작품의 에피소드 구매
     * @param rq content_id (Long) - 작품의 ID, episodeNumber (int) 작품의 에피소드 번호
     * @param request 클라이언트의 http 요청 객체(JWT 토큰을 추출하기 위해 사용됨)
     * @return 성공적으로 등록되었을 경우 응답 객체
     *       - 성공: HTTP 200 OK
     *       - 사용자 성인인증 실패: HTTP FORBIDDEN
     *       - 작품의 에피소드가 존재하지 않을 경우: HTTP FORBIDDEN
     *       - 사용자가 작품의 에피소드가 이미 구매한 경우: HTTP CONFLICT
     */
    @PostMapping
    public ApiRes<?> purchaseContent(@Valid @RequestBody MemberContentCreateRequest rq, HttpServletRequest request) {
        String token = jwtUtil.getJwtFromRequest(request);
        String memberId = jwtUtil.getMemberId(token);
        boolean isAdult = jwtUtil.getIsAdult(token);
        memberContentFacade.purchaseContent(rq.contentId(), Long.parseLong(memberId), isAdult, rq.episodeNumber());
        return ApiRes.createSuccessWithNoContent();
    }

}
