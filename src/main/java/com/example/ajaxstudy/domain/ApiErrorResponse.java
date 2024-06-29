package com.example.ajaxstudy.domain;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiErrorResponse<T> {

    private int code;
    private HttpStatus status;
    private String message;
    private T data;

    private ApiErrorResponse(final HttpStatus status, final String message, final T data) {
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T>ApiErrorResponse<T> of(HttpStatus status, String message, T data){
        return new ApiErrorResponse<>(status,message,data);
    }
}
