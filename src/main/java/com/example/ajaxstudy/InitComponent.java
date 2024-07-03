package com.example.ajaxstudy;

import com.example.ajaxstudy.domain.board.Board;
import com.example.ajaxstudy.domain.board.BoardRepository;
import com.example.ajaxstudy.domain.comment.Comment;
import com.example.ajaxstudy.domain.comment.CommentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitComponent {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    @PostConstruct
    public void init(){
        Board board = Board.builder()
                .writer("라이푸니")
                .title("안녕하세요")
                .contents("안녕하세요")
                .heartNum(0)
                .build();
        boardRepository.save(board);

        Comment parent = Comment.builder()
                .board(board)
                .writer("라이푸니")
                .contents("부모 댓글")
                .build();
        Comment parent2 = Comment.builder()
                .board(board)
                .writer("라이푸니2")
                .contents("부모2 댓글")
                .build();
        commentRepository.saveAll(List.of(parent,parent2));

        Comment child = Comment.builder()
                .board(board)
                .writer("라이푸니")
                .contents("자식 댓글")
                .build();
        child.changeParentComment(parent);
        commentRepository.save(child);
    }

}
