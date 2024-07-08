package com.example.ajaxstudy.domain.board;

import com.example.ajaxstudy.domain.board.Api.BoardController;
import com.example.ajaxstudy.domain.board.response.BoardDetailResponse;
import com.example.ajaxstudy.domain.board.response.BoardListResponse;
import com.example.ajaxstudy.domain.comment.CommentService;
import com.example.ajaxstudy.domain.comment.response.CommentBoardListResponse;
import com.example.ajaxstudy.domain.comment.response.CommentBoardResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = BoardController.class)
class BoardControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected BoardService boardService;

    @MockBean
    protected CommentService commentService;

    @DisplayName("게시물들을 최신순으로 조회한다.")
    @Test
    void boards() throws Exception {
        //given
        List<BoardListResponse> boards = Stream.of(
                Board.of("1", "1", "1"),
                Board.of("2", "2", "2"),
                Board.of("3", "3", "3")
        ).map(BoardListResponse::of).toList();

        Mockito.when(boardService.findAllDesc()).thenReturn(boards);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/boards"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("board/boards"));
    }

    @DisplayName("boardId를 받아 해당하는 게시글을 조회한다.")
    @Test
    void boardDetail() throws Exception {
        //given
        Long boardId = 0L;

        BoardDetailResponse response = BoardDetailResponse.builder()
                .writer("라이푸니")
                .title("제목")
                .heartNum(0)
                .contents("내용")
                .build();
        CommentBoardResponse boardResponse = CommentBoardResponse.builder()
                .writer("라이푸니")
                .commentId(0L)
                .contents("댓글내용")
                .build();

        Mockito.when(boardService.findById(Mockito.any(Long.class)))
                .thenReturn(response);
        Mockito.when(commentService.findAllByBoardIdAndNullDesc(Mockito.any(Long.class),Mockito.any(int.class)))
                .thenReturn(Mockito.any(CommentBoardListResponse.class));

        //when
        //then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/boards/" + boardId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("board/boardDetail"));
    }

}