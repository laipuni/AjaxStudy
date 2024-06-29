package com.example.ajaxstudy.domain.heart;

import com.example.ajaxstudy.domain.board.Board;
import com.example.ajaxstudy.domain.board.BoardRepository;
import com.example.ajaxstudy.domain.heart.request.HeartAddRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class HeartServiceTest {

    @Autowired
    protected HeartService heartService;

    @Autowired
    protected HeartRepository heartRepository;

    @Autowired
    protected BoardRepository boardRepository;

    @BeforeEach
    void tearUp(){
        heartRepository.deleteAllInBatch();
        boardRepository.deleteAllInBatch();
    }

    @DisplayName("해당 게시물에 좋아요를 누를 경우 게시물에 좋아요 개수를 올리고 좋아요를 저장한다.")
    @Test
    void addHeart(){
        //given
        int heartNum = 2;
        Board board = Board.builder()
                .writer("라이푸니")
                .title("제목")
                .contents("내용")
                .heartNum(heartNum)
                .build();

        boardRepository.save(board);
        HeartAddRequest request = HeartAddRequest.builder()
                .boardId(board.getId())
                .build();
        //when
        heartService.addHeart(request);
        List<Heart> hearts = heartRepository.findAll();
        List<Board> boards = boardRepository.findAll();

        //then
        assertThat(hearts).hasSize(1);
        assertThat(boards.get(0).getHeartNum()).isEqualTo(heartNum + 1);
    }

    @DisplayName("존재하지 않는 게시물에 좋아요를 할 경우 에러가 발생한다.")
    @Test
    void addHeartWithNoExistBoard(){
        //given
        HeartAddRequest request = HeartAddRequest.builder()
                .boardId(0L)
                .build();
        //when
        //then
        assertThatThrownBy(() -> heartService.addHeart(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("해당 게시물은 존재하지 않습니다.");
    }

}