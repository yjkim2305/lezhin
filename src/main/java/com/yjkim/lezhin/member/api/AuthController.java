package com.yjkim.lezhin.member.api;

import com.yjkim.lezhin.common.api.response.ApiRes;
import com.yjkim.lezhin.member.api.request.MemberCreateRequest;
import com.yjkim.lezhin.member.application.dto.MemberCreateCommand;
import com.yjkim.lezhin.member.application.service.AuthService;
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

    private final AuthService authService;

    @PostMapping("/signup")
    public ApiRes<?> signUpMember(@Valid @RequestBody MemberCreateRequest rq) {
        authService.signUpMember(MemberCreateCommand.from(rq));
        return ApiRes.createSuccessWithNoContent();
    }
}
