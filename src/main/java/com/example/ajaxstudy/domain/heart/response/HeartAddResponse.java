package com.example.ajaxstudy.domain.heart.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HeartAddResponse {

    private int heartNum;

    @Builder
    private HeartAddResponse(final int heartNum) {
        this.heartNum = heartNum;
    }

    public static HeartAddResponse of(final int heartNum) {
        return HeartAddResponse.builder()
                .heartNum(heartNum)
                .build();
    }
}
