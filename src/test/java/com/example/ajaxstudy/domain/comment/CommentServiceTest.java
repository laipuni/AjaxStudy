package com.example.ajaxstudy.domain.comment;

import com.example.ajaxstudy.domain.board.Board;
import com.example.ajaxstudy.domain.board.BoardRepository;
import com.example.ajaxstudy.domain.comment.request.CommentAddRequest;
import com.example.ajaxstudy.domain.comment.request.CommentModifyRequest;
import com.example.ajaxstudy.domain.comment.request.CommentReplyRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Profile("test")
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
        boardRepository.save(board);
        commentRepository.save(comment);

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

    @DisplayName("댓글을 Id를 받아와서 해당 Id에 댓글을 제거한다.")
    @Test
    void deleteByCommentId(){
        //given
        Board board = Board.builder()
                .writer("게시글 작성한 라이푸니")
                .heartNum(0)
                .title("제목")
                .contents("게시글 내용")
                .build();

        boardRepository.save(board);

        Comment comment = Comment.builder()
                .writer("댓글 작성한 라이푸니")
                .contents("댓글 내용")
                .board(board)
                .build();
        commentRepository.save(comment);

        //when
        commentService.deleteByCommentId(comment.getId());
        List<Comment> comments = commentRepository.findAll();

        //then
        assertThat(comments).isEmpty();
    }

    @DisplayName("댓글을 Id를 받아와서 해당 Id에 댓글을 제거하고 답글들도 삭제한다.")
    @Test
    void deleteByCommentIdWithChild(){
        //given
        Board board = Board.builder()
                .writer("게시글 작성한 라이푸니")
                .heartNum(0)
                .title("제목")
                .contents("게시글 내용")
                .build();

        boardRepository.save(board);

        Comment comment = Comment.builder()
                .writer("댓글 작성한 라이푸니")
                .contents("댓글 내용")
                .board(board)
                .build();
        commentRepository.save(comment);

        Comment child = Comment.builder()
                .writer("답글 작성한 라이푸니")
                .contents("답글 내용")
                .board(board)
                .build();

        child.changeParentComment(comment);
        commentRepository.save(child);

        //when
        commentService.deleteByCommentId(comment.getId());
        List<Comment> comments = commentRepository.findAll();

        //then
        assertThat(comments).isEmpty();
    }

    @DisplayName("댓글 수정 요청을 받아 해당 댓글을 수정 한다.")
    @Test
    void modifyComment(){
        //given
        Comment comment = Comment.builder()
                .writer("익명")
                .contents("Hello world!")
                .build();

        commentRepository.save(comment);

        String changeWriter = "라이푸니";
        String changeContents = "안녕하세요";
        CommentModifyRequest request = CommentModifyRequest.builder()
                .writer(changeWriter)
                .commentId(comment.getId())
                .contents(changeContents)
                .build();
        //when
        commentService.modifyComment(request);
        List<Comment> comments = commentRepository.findAll();

        //then
        assertThat(comments).hasSize(1)
                .extracting("writer","contents")
                .containsExactlyInAnyOrder(
                        tuple(changeWriter,changeContents)
                );
    }

    @DisplayName("존재하지 않는 댓글의 수정 요청을 받을 경우 에러가 발생한다.")
    @Test
    void modifyCommentWithNotExistComment(){
        //given
        String changeWriter = "라이푸니";
        String changeContents = "안녕하세요";
        CommentModifyRequest request = CommentModifyRequest.builder()
                .writer(changeWriter)
                .commentId(0L)
                .contents(changeContents)
                .build();
        //when

        //then
        assertThatThrownBy(()-> commentService.modifyComment(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("해당 댓글을 존재하지 않습니다.");
    }
}