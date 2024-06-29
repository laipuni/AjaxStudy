package com.example.ajaxstudy.domain.board.request;

import com.example.ajaxstudy.domain.board.Board;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardAddRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String contents;

    @NotBlank
    private String writer;

    public BoardAddRequest(final String title,final String contents, final String writer) {
        this.title = title;
        this.contents = contents;
        this.writer = writer;
    }

    public Board toEntity(){
        return Board.of(title,writer,contents);
    }
}
