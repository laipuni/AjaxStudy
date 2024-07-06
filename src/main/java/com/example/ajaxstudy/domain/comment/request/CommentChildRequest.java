package com.example.ajaxstudy.domain.comment.request;

import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentChildRequest {

    private Long commentId;

    @Positive(message = "해당하는 page가 없습니다.")
    private int page;

    @Builder
    private CommentChildRequest(final Long commentId, final int page) {
        this.commentId = commentId;
        this.page = page;
    }

}
