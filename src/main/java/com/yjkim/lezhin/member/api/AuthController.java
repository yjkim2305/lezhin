package com.yjkim.lezhin.member.api;

import com.yjkim.lezhin.common.api.response.ApiRes;
import com.yjkim.lezhin.member.api.request.MemberCreateRequest;
import com.yjkim.lezhin.member.api.request.MemberLoginRequest;
import com.yjkim.lezhin.member.api.response.MemberLoginResponse;
import com.yjkim.lezhin.member.application.dto.MemberCreateCommand;
import com.yjkim.lezhin.member.application.dto.MemberLoginCommand;
import com.yjkim.lezhin.member.application.dto.MemberLoginDto;
import com.yjkim.lezhin.member.application.service.AuthService;
import com.yjkim.lezhin.member.application.service.MemberService;
import com.yjkim.lezhin.member.facade.MemberFacade;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final MemberService memberService;
    private final AuthService authService;
    private final MemberFacade memberFacade;

    /***
     * 새로운 회원을 등록합니다.
     * @param rq MemberCreateRequest
     *           - memberEmail(String): 사용자 이메일(필수)
     *           - password(String): 비밀번호(필수)
     *           - memberName(String):  사용자 이름
     *           - birthDate(String): 생년월일(필수)
     * @return 성공적으로 등록되었을 경우 응답 객체
     *           - 성공: HTTP 200 OK
     *           - 유효성 검증 실패: HTTP 400 BAD REQUEST
     */
    @PostMapping("/signup")
    public ApiRes<?> signUpMember(@Valid @RequestBody MemberCreateRequest rq) {
        memberService.signUpMember(MemberCreateCommand.from(rq));
        return ApiRes.createSuccessWithNoContent();
    }

    /***
     * 로그인
     * @param rq MemberLoginRequest
     *           - memberEmail(String): 사용자 이메일(필수)
     *           - password(String): 비밀번호(필수)
     * @param response refresh 토큰을 쿠키로 생성하기 위함
     * @return 로그인 성공 200 OK
     *         - accessToken(String): accessToken
     *         - refreshToken(String): refreshToken
     *         로그인 실패(이메일 또는 비밀번호 불일치): HTTP 401
     */
    @PostMapping("/login")
    public ApiRes<MemberLoginResponse> loginMember(@Valid @RequestBody MemberLoginRequest rq, HttpServletResponse response) {
        MemberLoginDto memberLoginDto = memberFacade.login(MemberLoginCommand.from(rq));
        response.addCookie(authService.createRefreshCookie(memberLoginDto.refreshToken()));
        return ApiRes.createSuccess(MemberLoginResponse.from(memberLoginDto));
    }
}
