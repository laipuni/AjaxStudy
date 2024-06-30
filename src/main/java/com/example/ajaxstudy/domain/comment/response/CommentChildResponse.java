package com.example.ajaxstudy.domain.comment.response;

import com.example.ajaxstudy.domain.comment.Comment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentChildResponse {

    private Long commentId;
    private String writer;
    private String contents;

    @Builder
    private CommentChildResponse(final Long commentId, final String writer, final String contents) {
        this.commentId = commentId;
        this.writer = writer;
        this.contents = contents;
    }

    public static CommentChildResponse of(Comment comment) {
        return CommentChildResponse.builder()
                .commentId(comment.getId())
                .writer(comment.getWriter())
                .contents(comment.getContents())
                .build();
    }
}
