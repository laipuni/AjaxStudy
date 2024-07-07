package com.example.ajaxstudy.domain.comment;

import com.example.ajaxstudy.domain.board.Board;
import com.example.ajaxstudy.domain.board.BoardRepository;
import com.example.ajaxstudy.domain.comment.response.CommentBoardResponse;
import com.example.ajaxstudy.domain.comment.response.CommentChildResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@Transactional
class CommentQuerydslRepositoryTest {

    @Autowired
    private CommentQuerydslRepository commentQuerydslRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    @DisplayName("해당 댓글의 답글이 5개에 2개씩 조회할 경우 다음이 존재하는지 2 + 1개를 최신순으로 조회한다.")
    @Test
    void findAllByParentIdDesc(){
        //given
        Comment parent = Comment.builder()
                .writer("라이푸니")
                .contents("내용")
                .build();

        commentRepository.save(parent);

        Comment child1 = Comment.builder()
                .writer("라이푸니")
                .contents("내용")
                .build();

        Comment child2 = Comment.builder()
                .writer("라이푸니")
                .contents("내용")
                .build();

        Comment child3 = Comment.builder()
                .writer("라이푸니")
                .contents("내용")
                .build();

        Comment child4 = Comment.builder()
                .writer("라이푸니")
                .contents("내용")
                .build();

        Comment child5 = Comment.builder()
                .writer("라이푸니")
                .contents("내용")
                .build();

        child1.changeParentComment(parent);
        child2.changeParentComment(parent);
        child3.changeParentComment(parent);
        child4.changeParentComment(parent);
        child5.changeParentComment(parent);
        commentRepository.saveAll(List.of(child1,child2,child3,child4,child5));

        Pageable pageable = PageRequest.of(0,2);
        //when
        Slice<CommentChildResponse> result = commentQuerydslRepository.findAllByParentIdDesc(parent.getId(), pageable);

        //then
        assertThat(result.hasNext()).isTrue();
        assertThat(result.getContent()).hasSize(3);
    }

    @DisplayName("해당 게시물에 댓글이 3개에 2개씩 조회할 경우 다음이 존재하는지 2 + 1개를 최신순으로 조회한다.")
    @Test
    void findAllByBoardIdAndNullDesc(){
        //given
        Board board = Board.builder()
                .writer("라이푸니")
                .title("게시물 제목")
                .contents("게시물 내용")
                .heartNum(0)
                .build();
        boardRepository.save(board);

        Comment parent1 = Comment.builder()
                .writer("라이푸니")
                .contents("자식 0개")
                .board(board)
                .build();

        Comment parent2 = Comment.builder()
                .writer("라이푸니")
                .contents("자식 2개")
                .board(board)
                .build();

        Comment parent3 = Comment.builder()
                .writer("라이푸니")
                .contents("자식 1개")
                .board(board)
                .build();

        commentRepository.saveAll(List.of(parent1,parent2,parent3));

        Comment child1 = Comment.builder()
                .writer("라이푸니")
                .contents("내용")
                .board(board)
                .build();

        Comment child2 = Comment.builder()
                .writer("라이푸니")
                .contents("내용")
                .board(board)
                .build();

        Comment child3 = Comment.builder()
                .writer("라이푸니")
                .contents("내용")
                .board(board)
                .build();
        child1.changeParentComment(parent2);
        child2.changeParentComment(parent2);
        child3.changeParentComment(parent3);
        commentRepository.saveAll(List.of(child1,child2,child3));

        Pageable pageable = PageRequest.of(0,2);
        //when
        Slice<CommentBoardResponse> result = commentQuerydslRepository.findAllByBoardIdAndNullDesc(board.getId(), pageable);

        //then
        assertThat(result.hasNext()).isTrue();
        assertThat(result.getContent()).hasSize(3)
                .extracting("writer","contents","childNum")
                .containsExactlyInAnyOrder(
                        tuple("라이푸니","자식 0개",0),
                        tuple("라이푸니","자식 2개",2),
                        tuple("라이푸니","자식 1개",1)
                );
    }
}