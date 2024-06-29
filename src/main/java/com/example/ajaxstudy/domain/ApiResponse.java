package com.example.ajaxstudy.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {

    private int code;
    private HttpStatus status;
    private T data;

    private ApiResponse(final HttpStatus status,  final T data) {
        this.code = status.value();
        this.status = status;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(HttpStatus status, T data){
        return new ApiResponse<>(status,data);
    }

}
