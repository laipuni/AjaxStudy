package com.example.ajaxstudy.domain.board.response;

import com.example.ajaxstudy.domain.board.Board;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class BoardDetailResponse {

    private String title;
    private String contents;
    private String writer;
    private int heartNum;

    @Builder
    private BoardDetailResponse(final String title, final String contents, final String writer, final int heartNum) {
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.heartNum = heartNum;
    }

    public static BoardDetailResponse of(final Board board) {
        return BoardDetailResponse.builder()
                .title(board.getTitle())
                .contents(board.getContents())
                .writer(board.getWriter())
                .heartNum(board.getHeartNum())
                .build();
    }
}
