package com.example.ajaxstudy.domain;

import org.springframework.http.HttpStatus;

import java.util.Collection;

public class ApiCollectionResponse<T> {

    private int code;
    private HttpStatus status;
    private int size;
    private Collection<T> data;

    private ApiCollectionResponse(final HttpStatus status,  final Collection<T> data) {
        this.code = status.value();
        this.status = status;
        this.size = data.size();
        this.data = data;
    }

    public static <T> ApiCollectionResponse<T> of(final HttpStatus status, final Collection<T> data){
        return new ApiCollectionResponse<>(status,data);
    }

}
