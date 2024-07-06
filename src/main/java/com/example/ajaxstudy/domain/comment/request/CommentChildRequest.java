package com.example.ajaxstudy.domain.comment.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentChildRequest {

    private Long commentId;
    private int page;

    @Builder
    private CommentChildRequest(final Long commentId, final int page) {
        this.commentId = commentId;
        this.page = page;
    }

}
