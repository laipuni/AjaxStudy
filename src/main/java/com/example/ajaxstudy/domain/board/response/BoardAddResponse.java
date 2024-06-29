package com.example.ajaxstudy.domain.board.response;

import com.example.ajaxstudy.domain.board.Board;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public class BoardAddResponse {

    @NotBlank
    private String title;

    @NotBlank
    private String contents;

    @NotBlank
    private String writer;

    @Builder
    private BoardAddResponse(final String title, final String contents, final String writer) {
        this.title = title;
        this.contents = contents;
        this.writer = writer;
    }

    public static BoardAddResponse of(final Board board) {
        return BoardAddResponse.builder()
                .title(board.getTitle())
                .contents(builder().contents)
                .writer(board.getWriter())
                .build();
    }

}
