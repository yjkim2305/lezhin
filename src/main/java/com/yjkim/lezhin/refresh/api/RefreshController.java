package com.yjkim.lezhin.refresh.api;


import com.yjkim.lezhin.common.api.response.ApiRes;
import com.yjkim.lezhin.refresh.api.response.RefreshReissueResponse;
import com.yjkim.lezhin.refresh.application.dto.RefreshReissueDto;
import com.yjkim.lezhin.refresh.application.service.RefreshService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/refresh")
public class RefreshController {
    private final RefreshService refreshService;


    /***
     * 액세스 토큰 만료 시 쿠키에 저장된 리프레시 토큰으로 액세스 토큰 재발급
     * @param request HTTP 요청 객체 (리프레시 토큰을 포함한 쿠키가 있어야 함)
     * @param response response HTTP 응답 객체 (새로운 리프레시 토큰을 쿠키에 저장)
     * @return 성공적으로 재발급된 액세스 토큰과 리프레시 토큰을 포함한 응답
     */
    @PostMapping("/reissue")
    public ApiRes<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        RefreshReissueDto refreshReissueDto = refreshService.reissueToken(request);
        response.addCookie(refreshService.createRefreshCookie(refreshReissueDto.refreshToken()));
        return ApiRes.createSuccess(RefreshReissueResponse.from(refreshReissueDto));
    }
}
