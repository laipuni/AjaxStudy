package com.example.ajaxstudy.domain.board;

import com.example.ajaxstudy.domain.board.request.BoardAddRequest;
import com.example.ajaxstudy.domain.board.response.BoardListResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    protected BoardRepository boardRepository;

    @Autowired
    protected BoardService boardService;

    @BeforeEach
    void tearUp(){
        boardRepository.deleteAllInBatch();
    }

    @DisplayName("게시글 등록 request를 받으면 게시글을 등록한다.")
    @Test
    void post(){
        //given
        String title = "<제목>안녕하세요";
        String contents = "<내용>안녕하세요";
        String writer = "<작성자>라이푸니";
        BoardAddRequest request = new BoardAddRequest(title,contents,writer);

        //when
        boardService.post(request);
        List<Board> boards = boardRepository.findAll();

        //then
        assertThat(boards).hasSize(1)
                .extracting("title","contents","writer")
                .containsExactlyInAnyOrder(
                        tuple(title,contents,writer)
                );
    }

    @DisplayName("게시물들을 게시한 순서에 내림차순으로 조회한다.")
    @Test
    void findAllDesc(){
        //given
        Board board1 = Board.of("1","1","1");
        Board board2 = Board.of("2","2","2");
        Board board3 = Board.of("3","3","3");
        boardRepository.saveAll(List.of(board1,board2,board3));
        //when
        List<BoardListResponse> boards = boardService.findAllDesc();

        //then
        assertThat(boards).hasSize(3)
                .extracting("title","writer")
                .containsExactly(
                        tuple("3","3"),
                        tuple("2","2"),
                        tuple("1","1")
                );
    }

    @DisplayName("존재하지 않는 게시물 Id를 조회할 경우 에러가 발생한다.")
    @Test
    void findById(){
        //given
        //when
        //then
        assertThatThrownBy(()->boardService.findById(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("존재하지 않는 게시물입니다.");
    }
}