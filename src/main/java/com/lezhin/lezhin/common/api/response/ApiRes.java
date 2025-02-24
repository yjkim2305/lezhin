package com.lezhin.lezhin.common.api.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiRes<T> {
    private int status;
    private String message;
    private T data;


    public static <T> ApiRes<T> createSuccess(T data) {
        return new ApiRes<>(HttpStatus.OK.value(), "success", data);
    }

    public static ApiRes<?> createSuccessWithNoContent() {
        return new ApiRes<>(HttpStatus.OK.value(), "success", null);
    }

    public static ApiRes<?> error(int status, String message) {
        return new ApiRes<>(status, message, null);
    }

    private ApiRes(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}