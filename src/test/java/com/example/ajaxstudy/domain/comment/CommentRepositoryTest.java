package com.example.ajaxstudy.domain.comment;

import com.example.ajaxstudy.domain.board.Board;
import com.example.ajaxstudy.domain.board.BoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @Autowired
    protected CommentRepository commentRepository;

    @Autowired
    protected BoardRepository boardRepository;

    @DisplayName("조회할 답글들의 댓글의 Id를 받아 답글들을 조회한다.")
    @Test
    void findAllByParentIdDesc(){
        //given
        Board board = Board.builder()
                .writer("작성자")
                .title("제목")
                .heartNum(0)
                .contents("내용")
                .build();
        boardRepository.save(board);
        Comment parent = Comment.builder()
                .writer("부모")
                .contents("부모!")
                .board(board)
                .build();
        commentRepository.save(parent);
        
        String writer = "자식";
        String contents = "자식";
        Comment child = Comment.builder()
                .writer(writer)
                .contents(contents)
                .board(board)
                .build();

        child.changeParentComment(parent);
        commentRepository.save(child);

        //when
        List<Comment> childs = commentRepository.findAllByParentIdDesc(parent.getId());

        //then
        assertThat(childs).hasSize(1)
                .extracting("writer","contents")
                .containsExactlyInAnyOrder(
                        tuple("자식","자식")
                );

    }

    @DisplayName("parent Comment가 Null인 해당 게시물에 Comment를 최신순으로 조회한다.")
    @Test
    void findAllByNullDesc(){
        //given
        Board board = Board.builder()
                .writer("작성자")
                .title("제목")
                .heartNum(0)
                .contents("내용")
                .build();
        boardRepository.save(board);
        Comment parent = Comment.builder()
                .writer("부모")
                .contents("부모!")
                .board(board)
                .build();
        commentRepository.save(parent);
        String writer = "자식";
        String contents = "자식";
        Comment child = Comment.builder()
                .writer(writer)
                .contents(contents)
                .board(board)
                .build();

        child.changeParentComment(parent);
        commentRepository.save(child);
        //when
        List<Comment> comments = commentRepository.findAllByBoardIdAndNullDesc(board.getId());

        //then
        Assertions.assertThat(comments).hasSize(1)
                .extracting("writer","contents")
                .containsExactlyInAnyOrder(
                        tuple("부모","부모!")
                );
    }

}