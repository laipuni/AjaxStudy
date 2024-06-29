package com.example.ajaxstudy.domain.heart;


import com.example.ajaxstudy.domain.board.Board;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Heart {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Board board;

    @Builder
    private Heart(final Board board) {
        this.board = board;
    }

    public static Heart of(Board board){
        Heart heart = Heart.builder()
                .board(board)
                .build();
        board.addHeart(heart);
        return heart;
    }
}
