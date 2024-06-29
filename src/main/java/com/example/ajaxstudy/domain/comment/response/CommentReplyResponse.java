package com.example.ajaxstudy.domain.comment.response;

import com.example.ajaxstudy.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentReplyResponse {


    private String content;

    @Builder
    private CommentReplyResponse(final String content) {
        this.content = content;
    }

    public static CommentReplyResponse of(Comment comment){
        return CommentReplyResponse.builder()
                .content(comment.getContents())
                .build();
    }

}
