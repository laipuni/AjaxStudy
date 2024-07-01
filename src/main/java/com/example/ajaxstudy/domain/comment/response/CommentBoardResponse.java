package com.example.ajaxstudy.domain.comment.response;

import com.example.ajaxstudy.domain.comment.Comment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentBoardResponse {

    private Long commentId;
    private String writer;
    private String contents;

    @Builder
    private CommentBoardResponse(final Long commentId, final String writer, final String contents) {
        this.commentId = commentId;
        this.writer = writer;
        this.contents = contents;
    }

    public static CommentBoardResponse of(final Comment comment) {
        return CommentBoardResponse.builder()
                .commentId(comment.getId())
                .contents(comment.getContents())
                .writer(comment.getWriter())
                .build();
    }
}
