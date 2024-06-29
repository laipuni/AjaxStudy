package com.example.ajaxstudy.domain.comment.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReplyRequest {

    private Long boardId;

    private Long commentId;

    @NotBlank(message = "댓글을 입력해주세요")
    private String contents;

    @NotBlank(message = "작성자의 이름을 입력해주세요")
    private String writer;

    @Builder
    private CommentReplyRequest(final Long boardId, final Long commentId, final String contents, final String writer) {
        this.boardId = boardId;
        this.commentId = commentId;
        this.contents = contents;
        this.writer = writer;
    }

}
