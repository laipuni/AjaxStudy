package com.example.ajaxstudy.domain.comment.response;

import com.example.ajaxstudy.domain.comment.Comment;
import lombok.Builder;

public class CommentChildResponse {

    private Long commentId;

    @Builder
    private CommentChildResponse(final Long commentId) {
        this.commentId = commentId;
    }

    public static CommentChildResponse of(Comment comment) {
        return CommentChildResponse.builder()
                .commentId(comment.getId())
                .build();
    }
}
