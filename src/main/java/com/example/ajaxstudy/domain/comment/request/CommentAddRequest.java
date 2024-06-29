package com.example.ajaxstudy.domain.comment.request;

import com.example.ajaxstudy.domain.board.Board;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentAddRequest {

    private Long boardId;

    @NotBlank(message = "댓글을 입력해주세요.")
    private String contents;

    @NotBlank(message = "작성자의 이름을 입력해주세요")
    private String writer;

    @Builder
    private CommentAddRequest(final Long boardId, final String contents, final String writer) {
        this.boardId = boardId;
        this.contents = contents;
        this.writer = writer;
    }

}
