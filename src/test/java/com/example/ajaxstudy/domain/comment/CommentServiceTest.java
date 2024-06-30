package com.example.ajaxstudy.domain.comment;

import com.example.ajaxstudy.domain.board.Board;
import com.example.ajaxstudy.domain.board.BoardRepository;
import com.example.ajaxstudy.domain.comment.request.CommentAddRequest;
import com.example.ajaxstudy.domain.comment.request.CommentChildRequest;
import com.example.ajaxstudy.domain.comment.request.CommentReplyRequest;
import com.example.ajaxstudy.domain.comment.response.CommentAddResponse;
import com.example.ajaxstudy.domain.comment.response.CommentChildResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    protected CommentRepository commentRepository;

    @Autowired
    protected CommentService commentService;

    @Autowired
    protected BoardRepository boardRepository;

    @BeforeEach
    void tearUp(){
        commentRepository.deleteAll();
        boardRepository.deleteAllInBatch();
    }

    @DisplayName("게시물에 댓글 등록할 경우 해당 게시물의 id과 댓글 내용, 작성자를 받아 댓글을 저장한다.")
    @Test
    void register(){
        //given
        Board board = Board.of("제목","작성자","내용");
        boardRepository.save(board);

        String writer = "라이푸니";
        String content = "안녕하세여";
        CommentAddRequest request = CommentAddRequest.builder()
                .writer(writer)
                .boardId(board.getId())
                .contents(content)
                .build();
        //when
        commentService.record(request);
        List<Comment> comments = commentRepository.findAll();

        //then
        assertThat(comments).hasSize(1)
                .extracting("contents","writer")
                .containsExactlyInAnyOrder(
                        tuple(content,writer)
                );
    }

    @DisplayName("없는 게시물에 댓글을 작성할 경우 에러가 발생한다.")
    @Test
    void registerWithNotExistBoard(){
        //given
        String writer = "라이푸니";
        String content = "안녕하세여";
        CommentAddRequest request = CommentAddRequest.builder()
                .writer(writer)
                .boardId(0L)
                .contents(content)
                .build();
        //when
        //then
        assertThatThrownBy(() -> commentService.record(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("해당 게시물은 존재하지 않습니다.");
    }

    @DisplayName("답글 등록할 경우 해당 답장할 댓글의 id과 답글 내용, 작성자를 받아 답글을 저장한다.")
    @Test
    void reply(){
        //given
        Board board = Board.of("제목","작성자","내용");
        String parent = "부모";
        String parentContent = "안녕하세요?";
        Comment comment = Comment.builder()
                .writer(parent)
                .board(board)
                .contents(parentContent)
                .build();
        commentRepository.save(comment);
        boardRepository.save(board);

        String child = "자식";
        String childContent = "안녕하세여!";
        CommentReplyRequest request = CommentReplyRequest.builder()
                .writer(child)
                .commentId(comment.getId())
                .boardId(board.getId())
                .contents(childContent)
                .build();
        //when
        commentService.reply(request);
        List<Comment> comments = commentRepository.findAll();

        //then
        assertThat(comments).hasSize(2)
                .extracting("contents","writer")
                .containsExactlyInAnyOrder(
                        tuple(parentContent,parent),
                        tuple(childContent,child)
                );
    }

    @DisplayName("없는 게시물에 답글을 작성할 경우 에러가 발생한다.")
    @Test
    void replyWithNotExistBoard(){
        //given
        String writer = "라이푸니";
        String content = "안녕하세여";
        CommentReplyRequest request = CommentReplyRequest.builder()
                .writer(writer)
                .boardId(0L)
                .commentId(0L)
                .contents(content)
                .build();
        //when
        //then
        assertThatThrownBy(() -> commentService.reply(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("해당 게시물은 존재하지 않습니다.");
    }

    @DisplayName("없는 댓글에 답글을 작성할 경우 에러가 발생한다.")
    @Test
    void replyWithNotExistComment(){
        //given
        Board board = Board.of("제목","작성자","내용");
        boardRepository.save(board);

        String writer = "라이푸니";
        String content = "안녕하세여";
        CommentReplyRequest request = CommentReplyRequest.builder()
                .writer(writer)
                .boardId(board.getId())
                .commentId(0L)
                .contents(content)
                .build();
        //when
        //then
        assertThatThrownBy(() -> commentService.reply(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("해당 댓글은 존재하지 않습니다.");
    }

    @DisplayName("조회할 답글들의 댓글이 존재하지 않을 경우 에러가 발생한다.")
    @Test
    void findAllByParentIdWithNoExistComment(){
        //given
        CommentChildRequest request = CommentChildRequest.builder()
                .commentId(0L)
                .build();

        //when
        //then
        assertThatThrownBy(() -> commentService.findAllByParentId(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("해당 댓글은 존재하지 않습니다.");
    }
}