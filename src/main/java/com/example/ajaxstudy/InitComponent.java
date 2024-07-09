package com.example.ajaxstudy;

import com.example.ajaxstudy.domain.board.Board;
import com.example.ajaxstudy.domain.board.BoardRepository;
import com.example.ajaxstudy.domain.comment.Comment;
import com.example.ajaxstudy.domain.comment.CommentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Profile("local")
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

        for (int i = 0; i < 20; i++) {
            Comment comment = Comment.builder()
                    .board(board)
                    .writer("라이푸니" + i)
                    .contents("부모"+ i +" 댓글")
                    .build();
            commentRepository.saveAll(List.of(comment));
        }

        Comment parent = Comment.builder()
                .board(board)
                .writer("라이푸니")
                .contents("부모 댓글")
                .build();
        commentRepository.save(parent);

        for (int i = 0; i < 20; i++) {
            Comment child = Comment.builder()
                    .board(board)
                    .writer("자식 라이푸니" + i)
                    .contents("자식"+ i +" 댓글")
                    .build();
            child.changeParentComment(parent);
            commentRepository.save(child);
        }
    }

}
