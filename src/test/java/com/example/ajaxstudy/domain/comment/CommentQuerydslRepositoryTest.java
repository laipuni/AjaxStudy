package com.example.ajaxstudy.domain.comment;

import com.example.ajaxstudy.domain.comment.response.CommentChildResponse;
import org.assertj.core.api.Assertions;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentQuerydslRepositoryTest {

    @Autowired
    private CommentQuerydslRepository commentQuerydslRepository;

    @Autowired
    private CommentRepository commentRepository;

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

}