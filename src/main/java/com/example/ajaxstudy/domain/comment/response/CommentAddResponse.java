package com.example.ajaxstudy.domain.comment.response;

import com.example.ajaxstudy.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentAddResponse {

    private String content;

    @Builder
    private CommentAddResponse(final String content) {
        this.content = content;
    }

    public static CommentAddResponse of(Comment comment){
        return CommentAddResponse.builder()
                .content(comment.getContents())
                .build();
    }
}
