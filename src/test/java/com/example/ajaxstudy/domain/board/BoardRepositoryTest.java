package com.example.ajaxstudy.domain.board;

import com.example.ajaxstudy.domain.board.response.BoardListResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    protected BoardRepository boardRepository;

    @DisplayName("게시물들을 최신순으로 조회한다.")
    @Test
    void findAllDesc(){
        //given
        Board board1 = Board.of("1","1","1");
        Board board2 = Board.of("2","2","2");
        Board board3 = Board.of("3","3","3");
        boardRepository.saveAll(List.of(board1,board2,board3));
        //when
        List<Board> boards = boardRepository.findAllOrderByBoardIdDesc();

        //then
        Assertions.assertThat(boards).hasSize(3)
                .extracting("title","writer","contents")
                .containsExactly(
                        tuple("3","3","3"),
                        tuple("2","2","2"),
                        tuple("1","1","1")
                );
    }

}