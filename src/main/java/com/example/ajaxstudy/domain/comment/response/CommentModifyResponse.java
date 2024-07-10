package com.example.ajaxstudy.domain.comment.response;

import com.example.ajaxstudy.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentModifyResponse {

    private String writer;
    private String contents;

    @Builder
    private CommentModifyResponse(final String writer, final String contents) {
        this.writer = writer;
        this.contents = contents;
    }

    public static CommentModifyResponse of(final Comment comment) {
        return CommentModifyResponse.builder()
                .writer(comment.getWriter())
                .contents(comment.getContents())
                .build();
    }
}
