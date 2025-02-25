package com.yjkim.lezhin.memberContent.api.exception;

import com.yjkim.lezhin.common.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum MemberContentErrorType implements ErrorType {
    EXIST_CONTENT_EPISODE(HttpStatus.CONFLICT, "이미 구매한 작품의 에피소드입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    MemberContentErrorType(HttpStatus httpStatus, String message) {
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
