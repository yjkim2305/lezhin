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

    @PostMapping("/signup")
    public ApiRes<?> signUpMember(@Valid @RequestBody MemberCreateRequest rq) {
        memberService.signUpMember(MemberCreateCommand.from(rq));
        return ApiRes.createSuccessWithNoContent();
    }

    @PostMapping("/login")
    public ApiRes<MemberLoginResponse> loginMember(@Valid @RequestBody MemberLoginRequest rq, HttpServletResponse response) {
        MemberLoginDto memberLoginDto = memberFacade.login(MemberLoginCommand.from(rq));
        response.addCookie(authService.createRefreshCookie(memberLoginDto.refreshToken()));
        return ApiRes.createSuccess(MemberLoginResponse.from(memberLoginDto));
    }
}
