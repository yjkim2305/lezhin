package com.yjkim.lezhin.content.api.exception;

import com.yjkim.lezhin.common.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum ContentErrorType implements ErrorType {
    NOT_EXIST_CONTENT(HttpStatus.NOT_FOUND, "해당 작품이 존재하지 않습니다."),
    FORBIDDEN_ADULT_CONTENT(HttpStatus.FORBIDDEN, "해당 작품은 성인 전용입니다."),
    FORBIDDEN_EPISODE_CONTENT(HttpStatus.FORBIDDEN, "해당 작품에 해당 에피소드가 존재하지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ContentErrorType(HttpStatus httpStatus, String message) {
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
