package com.example.ajaxstudy.domain.comment.Api;

import com.example.ajaxstudy.domain.comment.CommentService;
import com.example.ajaxstudy.domain.comment.request.CommentAddRequest;
import com.example.ajaxstudy.domain.comment.request.CommentReplyRequest;

import com.example.ajaxstudy.domain.comment.response.CommentChildResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CommentApiController.class)
class CommentApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected CommentService commentService;

    @DisplayName("해당 게시글에 댓글을 작성할 경우, 댓글을 등록하고 결과를 반환한다.")
    @Test
    void addComment() throws Exception {
        //given
        CommentAddRequest request = CommentAddRequest.builder()
                .boardId(0L)
                .writer("라이푸니")
                .contents("내용")
                .build();
        String data = objectMapper.writeValueAsString(request);

        //when//then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("존재하지 않는 게시글에 댓글을 작성할 경우, 에러가 발생한다.")
    @Test
    void addCommentWithNoExistBoard() throws Exception {
        //given
        CommentAddRequest request = CommentAddRequest.builder()
                .boardId(0L)
                .writer("라이푸니")
                .contents("내용")
                .build();
        String data = objectMapper.writeValueAsString(request);

        Mockito.when(commentService.record(Mockito.any(CommentAddRequest.class)))
                        .thenThrow(new IllegalArgumentException("해당 게시물은 존재하지 않습니다."));

        //when//then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/comment")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(data)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("해당 게시물은 존재하지 않습니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("빈 댓글을 작성할 경우, 에러가 발생한다.")
    @Test
    void addCommentWithBlankContents() throws Exception {
        //given
        CommentAddRequest request = CommentAddRequest.builder()
                .boardId(0L)
                .writer("라이푸니")
                .contents(" ")
                .build();
        String data = objectMapper.writeValueAsString(request);

        //when//then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/comment")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(data)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("댓글을 입력해주세요."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("작성자이름없이 댓글을 작성할 경우, 에러가 발생한다.")
    @Test
    void addCommentWithBlankWriter() throws Exception {
        //given
        CommentAddRequest request = CommentAddRequest.builder()
                .boardId(0L)
                .writer(" ")
                .contents("안녕하세요")
                .build();
        String data = objectMapper.writeValueAsString(request);

        //when//then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/comment")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(data)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("작성자의 이름을 입력해주세요"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("해당 댓글에 답글을 작성할 경우, 답글을 등록하고 결과를 반환한다.")
    @Test
    void addReply() throws Exception {
        //given
        CommentReplyRequest request = CommentReplyRequest.builder()
                .boardId(1L)
                .commentId(1L)
                .writer("라이푸니")
                .contents("내용")
                .build();
        String data = objectMapper.writeValueAsString(request);

        //when//then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/comment/reply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(data)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("존재하지 않는 게시글에 답글을 작성할 경우, 에러가 발생한다.")
    @Test
    void addReplyWithNoExistBoard() throws Exception {
        //given
        CommentReplyRequest request = CommentReplyRequest.builder()
                .boardId(1L)
                .commentId(1L)
                .writer("라이푸니")
                .contents("내용")
                .build();

        String data = objectMapper.writeValueAsString(request);

        Mockito.when(commentService.reply(Mockito.any(CommentReplyRequest.class)))
                .thenThrow(new IllegalArgumentException("해당 게시물은 존재하지 않습니다."));

        //when//then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/comment/reply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(data)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("해당 게시물은 존재하지 않습니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("빈 답글을 작성할 경우, 에러가 발생한다.")
    @Test
    void addReplyWithBlankContents() throws Exception {
        //given
        CommentReplyRequest request = CommentReplyRequest.builder()
                .boardId(1L)
                .commentId(1L)
                .writer("라이푸니")
                .contents(" ")
                .build();

        String data = objectMapper.writeValueAsString(request);

        //when//then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/comment")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(data)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("댓글을 입력해주세요."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("작성자이름없이 답글을 작성할 경우, 에러가 발생한다.")
    @Test
    void addReplyWithBlankWriter() throws Exception {
        //given
        CommentReplyRequest request = CommentReplyRequest.builder()
                .boardId(1L)
                .commentId(1L)
                .writer(" ")
                .contents("내용")
                .build();
        String data = objectMapper.writeValueAsString(request);

        //when//then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/comment/reply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(data)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("작성자의 이름을 입력해주세요"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("존재하지 않는 댓글에 답글을 작성할 경우, 에러가 발생한다.")
    @Test
    void addReplyWithNoExistComment() throws Exception {
        //given
        CommentReplyRequest request = CommentReplyRequest.builder()
                .boardId(1L)
                .commentId(1L)
                .writer("라이푸니")
                .contents("내용")
                .build();
        String data = objectMapper.writeValueAsString(request);

        Mockito.when(commentService.reply(Mockito.any(CommentReplyRequest.class)))
                .thenThrow(new IllegalArgumentException("해당 댓글은 존재하지 않습니다."));

        //when//then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/comment/reply")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(data)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("해당 댓글은 존재하지 않습니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("댓글의 Id를 받고, 해당 댓글의 답글들을 조회한 뒤 client로 응답한다.")
    @Test
    void getChild() throws Exception {
        //given
        Long commentId = 0L;
        String writer = "작성자";
        String contents = "내용";

        Mockito.when(commentService.findAllByParentId(Mockito.any(Long.class)))
                .thenReturn(List.of(CommentChildResponse.builder()
                                .writer(writer)
                                .contents(contents)
                                .commentId(commentId)
                                .build())
                );

        //when//then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/comment/" + commentId + "/reply")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.size").value(1))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].writer").value(writer))
                .andExpect(jsonPath("$.data[0].contents").value(contents));
    }
}