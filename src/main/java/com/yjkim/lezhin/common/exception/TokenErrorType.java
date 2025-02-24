package com.yjkim.lezhin.common.exception;

import org.springframework.http.HttpStatus;

public enum TokenErrorType implements ErrorType {
    EXPIRE_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "적합하지 않은 토큰 입니다.")
    ;


    private final HttpStatus httpStatus;
    private final String message;

    TokenErrorType(HttpStatus httpStatus, String message) {
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
