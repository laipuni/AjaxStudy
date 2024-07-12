package com.example.ajaxstudy.domain.comment;

import com.example.ajaxstudy.domain.board.Board;
import com.example.ajaxstudy.domain.board.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Profile("test")
@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @Autowired
    protected CommentRepository commentRepository;

    @Autowired
    protected BoardRepository boardRepository;

    @DisplayName("해당 Id에 댓글의 자식들을 삭제한다.")
    @Test
    void deleteChildAllByCommentId(){
        //given
        Board board = Board.builder()
                .title("제목")
                .writer("작성자")
                .contents("내용")
                .heartNum(0)
                .build();

        boardRepository.save(board);

        Comment parent = Comment.builder()
                .writer("부모")
                .contents("부모 댓글")
                .build();

        commentRepository.save(parent);

        Comment child = Comment.builder()
                .writer("자식")
                .contents("부모 댓글")
                .build();

        child.changeParentComment(parent);
        commentRepository.save(child);

        //when
        commentRepository.deleteChildAllByCommentId(parent.getId());
        List<Comment> comments = commentRepository.findAll();

        //then
        assertThat(comments).hasSize(1)
                .extracting("writer","contents")
                .containsExactlyInAnyOrder(
                        tuple("부모","부모 댓글")
                );
    }

    @DisplayName("해당 Id에 댓글을 삭제한다.")
    @Test
    void deleteById(){
        //given
        Board board = Board.builder()
                .title("제목")
                .writer("작성자")
                .contents("내용")
                .heartNum(0)
                .build();

        boardRepository.save(board);

        Comment comment = Comment.builder()
                .writer("라이푸니")
                .contents("댓글 내용")
                .build();

        commentRepository.save(comment);

        //when
        commentRepository.deleteById(comment.getId());
        List<Comment> comments = commentRepository.findAll();

        //then
        assertThat(comments).isEmpty();
    }
}