package com.yjkim.lezhin.refresh.api.exception;

import com.yjkim.lezhin.common.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum RefreshErrorType implements ErrorType {
    NOT_EXIST_AUTHORIZATION(HttpStatus.BAD_REQUEST, "토큰이 존재하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "적합하지 않은 토큰 입니다.")
    ;


    private final HttpStatus httpStatus;
    private final String message;

    RefreshErrorType(HttpStatus httpStatus, String message) {
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
