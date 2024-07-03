package com.example.ajaxstudy.domain.comment.response;

import com.example.ajaxstudy.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentReplyResponse {

    private Long commentId;
    private String content;
    private String writer;

    @Builder
    private CommentReplyResponse(final Long commentId, final String content, final String writer) {
        this.commentId = commentId;
        this.content = content;
        this.writer = writer;
    }

    public static CommentReplyResponse of(Comment comment){
        return CommentReplyResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContents())
                .writer(comment.getWriter())
                .build();
    }

}
