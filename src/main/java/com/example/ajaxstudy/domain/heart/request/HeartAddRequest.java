package com.example.ajaxstudy.domain.heart.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HeartAddRequest {

    private Long boardId;

    @Builder
    private HeartAddRequest(final Long boardId) {
        this.boardId = boardId;
    }

}
