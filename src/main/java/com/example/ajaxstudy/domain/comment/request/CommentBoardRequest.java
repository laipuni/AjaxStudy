package com.example.ajaxstudy.domain.comment.request;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentBoardRequest {

    private Long boardId;

    @PositiveOrZero(message = "해당하는 page가 없습니다.")
    private int page;

    @Builder
    private CommentBoardRequest(final Long boardId, final int page) {
        this.boardId = boardId;
        this.page = page;
    }
}
