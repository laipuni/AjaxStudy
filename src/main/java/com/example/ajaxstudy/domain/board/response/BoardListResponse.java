package com.example.ajaxstudy.domain.board.response;

import com.example.ajaxstudy.domain.board.Board;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardListResponse {

    private Long boardId;
    private String writer;
    private String title;
    private int heartNum;

    @Builder
    private BoardListResponse(final Long boardId, final String writer, final String title, final int heartNum) {
        this.boardId = boardId;
        this.writer = writer;
        this.title = title;
        this.heartNum = heartNum;
    }

    public static BoardListResponse of(Board board){
        return BoardListResponse.builder()
                .boardId(board.getId())
                .heartNum(board.getHeartNum())
                .writer(board.getWriter())
                .title(board.getTitle())
                .build();
    }

}
