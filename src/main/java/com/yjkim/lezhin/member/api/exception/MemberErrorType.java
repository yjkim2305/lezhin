package com.yjkim.lezhin.member.api.exception;

import com.yjkim.lezhin.common.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum MemberErrorType implements ErrorType {
    EXIST_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 회원입니다."),
    NOT_EXIST_USER_PASSWORD(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 잘못되었습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    MemberErrorType(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getErrorCode() {
        return this.name();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
