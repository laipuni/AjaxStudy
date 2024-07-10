package com.example.ajaxstudy.domain.comment.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentModifyRequest {

    @NotNull(message = "해당 항목은 필수입니다.")
    private Long commentId;

    @NotBlank(message = "해당 항목은 필수입니다.")
    private String writer;

    @NotBlank(message = "해당 항목은 필수입니다.")
    private String contents;

    @Builder
    private CommentModifyRequest(final Long commentId, final String writer, final String contents) {
        this.commentId = commentId;
        this.writer = writer;
        this.contents = contents;
    }

}
